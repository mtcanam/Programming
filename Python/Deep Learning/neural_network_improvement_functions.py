import numpy as np


def initialize_parameters_random(layer_dims):
    """"
    Purpose: Random initialization of weights and biases to prevent symmetry from stalling a model

    Inputs:
    layer_dims -- An array detailing the size of all teh layers

    Outputs:
    parameters -- A dictionary containing the initialized vector and bias ndarrays
    """

    parameters = {}
    L = len(layer_dims)  # integer representing the number of layers

    for l in range(1, L):
        parameters['W' + str(l)] = np.random.randn(layer_dims[l], layer_dims[l - 1]) * 0.01
        parameters['b' + str(l)] = np.zeros((layer_dims[l], 1))

    return parameters


def initialize_parameters_he(layer_dims):
    """
    Purpose: Random initialization of weights and biases to prevent symmetry from stalling a model. Adds a scaling
    factor to the weight arrays based on their size.

    Inputs:
    layer_dims -- An array detailing the size of all the layers

    Outputs:
    parameters -- A dictionary containing the initialized vector and bias ndarrays
    """

    parameters = {}
    L = len(layer_dims)  # integer representing the number of layers

    for l in range(1, L):
        parameters['W' + str(l)] = np.random.randn(layer_dims[l], layer_dims[l - 1]) * np.sqrt(2. / layer_dims[l - 1])
        parameters['b' + str(l)] = np.zeros((layer_dims[l], 1))

    return parameters


def compute_cost(AL, Y):
    """
    Purpose: Computes the cross-entropt cost of the

    Inputs:
    AL -- Activation layer matrix for the output layer
    Y -- Target layer

    Outputs:
    cost -- single number representing the total log loss of the neural network
    """

    m = Y.shape[1]

    # Compute loss from aL and y.
    cost = -1 / m * np.sum(np.multiply(np.log(AL), Y) + (1 - Y) * np.log(1 - AL))

    cost = np.squeeze(cost)  # To make sure your cost's shape is what we expect (e.g. this turns [[17]] into 17).
    assert (cost.shape == ())

    return cost


def compute_cost_with_regularization(AL, Y, parameters, lambd):
    """
    Purpose: Computes the cost of the model with added regularization parameters to prevent overfitting

    Inputs:
    AL -- the final activation layer of the model
    Y -- the target vector of the model
    parameters -- a dictionary of weights and biases
    lambd -- a scalar that penalizes high weights

    Outputs:
    cost -- a scalar representing the level of fit of the model (lower is better, usually)
    """
    m = Y.shape[1]
    L = len(parameters) // 2 + 1

    cross_entropy_cost = compute_cost(AL, Y)  # This gives you the cross-entropy part of the cost

    L2_regularization_cost = 0

    for i in range(1, L):
        W = parameters["W" + str(i)]
        L2_regularization_cost += lambd / (2 * m) * np.sum(np.square(W))

    cost = cross_entropy_cost + L2_regularization_cost

    return cost


def backward_propagation_with_regularization(X, Y, cache, lambd):
    """
    Purpose: Computes the gradients of a model with added regularization parameters to prevent overfitting

    Inputs:
    X -- the input layer of the model
    Y -- the target vector of the model
    cache -- a dictionary of weights and biases, activations, and pre-activations (W, b, A, Z)
    lambd -- a scalar that penalizes high weights

    Outputs:
    gradients -- a dictionary of gradients (dW, db, dA)
    """

    L = len(cache) // 4
    gradients = {}
    m = X.shape[1]

    for i in reversed(range(1, L + 1)):
        if i == L:
            Ai = cache["A" + str(i)]
            dZi = Ai - Y

        else:
            Ai = cache["A" + str(i)]
            dAi = np.dot(cache["W" + str(i + 1)].T, gradients["dZ" + str(i + 1)])
            dZi = np.multiply(dAi, np.int64(Ai > 0))
            gradients["dA" + str(i)] = dAi

        gradients["dZ" + str(i)] = dZi
        dbi = 1. / m * np.sum(dZi, axis=1, keepdims=True)
        gradients["db" + str(i)] = dbi
        if i > 1:
            dWi = 1. / m * np.dot(dZi, cache["A" + str(i - 1)].T) + lambd / m * cache["W" + str(i)]
        else:
            dWi = 1. / m * np.dot(dZi, X.T) + lambd / m * cache["W" + str(i)]
        gradients["dW" + str(i)] = dWi

    return gradients


def tuple_to_dict(tuple):
    # Simple accessory function to convert a tuple of 4 repeating sets of arrays (Zi, Ai, Wi, bi) to a dictionary for
    # testing

    dict = {}
    # ZAWb
    for i in range(1, len(tuple) + 1):
        if i % 4 == 0:
            dict["Z" + str(i // 4)] = tuple[i]
        elif i % 4 == 1:
            dict["A" + str(i // 4)] = tuple[i]
        elif i % 4 == 2:
            dict["W" + str(i // 4)] = tuple[i]
        elif i % 4 == 3:
            dict["b" + str(i // 4)] = tuple[i]

    return dict


def sigmoid(z):
    # Returns the sigmoid function of z
    s = 1 / (1 + np.exp(-1 * z))
    return s


def relu(z):
    # Returns the rectified linear unit of z
    return z * (z > 0)


def forward_propagation_with_dropout(X, parameters, keep_prob=0.5):
    """
    Purpose: Computes the forward propagation step with each neuron having a keep_prob probability of being used

    Inputs:
    X -- the input layer of the model
    parameters -- a dictionary of weights and biases
    keep_prob -- a scalar that represents the probability of keeping each individual neuron

    Outputs:
    AL -- the output layer
    cache -- a dictionary of weights, biases, activations, and pre-activations
    """

    L = len(parameters) // 2

    cache = {}

    for i in range(1, L):
        Wi = parameters["W" + str(i)]
        bi = parameters["b" + str(i)]
        if i == 1:
            A_prev = X
        else:
            A_prev = cache["A" + str(i - 1)]
        Zi = np.dot(Wi, A_prev) + bi
        Ai = relu(Zi)
        Di = np.random.rand(Ai.shape[0], Ai.shape[1])  # Initialize a random matrix in the shape of Ai
        Di = (Di < keep_prob)  # Convert entries of Di to 0 or 1 (using keep_prob as the threshold)
        Ai = Ai * Di  # Shut down some neurons of Ai
        Ai = Ai / keep_prob  # Scale the value of neurons that haven't been shut down
        cache["Z" + str(i)] = Zi
        cache["D" + str(i)] = Di
        cache["A" + str(i)] = Ai
        cache["W" + str(i)] = Wi
        cache["b" + str(i)] = bi

    ZL = np.dot(parameters["W" + str(L)], cache["A" + str(L-1)]) + parameters["b" + str(L)]
    AL = sigmoid(ZL)

    cache["Z" + str(L)] = ZL
    cache["D" + str(L)] = np.ones(AL.shape[0], AL.shape[1])
    cache["A" + str(L)] = AL
    cache["W" + str(L)] = parameters["W" + str(L)]
    cache["b" + str(L)] = parameters["b" + str(L)]

    return AL, cache


def backward_propagation_with_dropout(X, Y, cache, keep_prob):
    """
    Purpose: Computes the backward propagation step with each neuron having a keep_prob probability of being used

    Inputs:
    X -- the input layer of the model
    Y -- The target vector of the model
    cache -- a dictionary of weights, biases, activations, pre-activations, and on-off vectors
    keep_prob -- a scalar that represents the probability of keeping each individual neuron

    Outputs:
    gradients -- a dictionary of gradients (dW, db, dA)
    """

    L = len(cache) // 5
    gradients = {}
    m = X.shape[1]

    for i in reversed(range(1, L + 1)):
        if i == L:
            Ai = cache["A" + str(i)]
            dZi = Ai - Y

        else:
            Ai = cache["A" + str(i)]
            Di = cache["D" + str(i)]
            dAi = np.dot(cache["W" + str(i + 1)].T, gradients["dZ" + str(i + 1)])
            dAi = Di * dAi
            dAi = dAi / keep_prob
            dZi = np.multiply(dAi, np.int64(Ai > 0))
            gradients["dA" + str(i)] = dAi

        gradients["dZ" + str(i)] = dZi
        dbi = 1. / m * np.sum(dZi, axis=1, keepdims=True)
        gradients["db" + str(i)] = dbi
        if i > 1:
            dWi = 1. / m * np.dot(dZi, cache["A" + str(i - 1)].T)
        else:
            dWi = 1. / m * np.dot(dZi, X.T)
        gradients["dW" + str(i)] = dWi

    return gradients

# TODO: Need to create helper functions
def gradient_check_n(parameters, gradients, X, Y, epsilon=1e-7):
    """
    Purpose: Check whether the backward propagation works correctly by using central difference method to approx d/dx

    Inputs:
    parameters -- dictionary containing weights and biases
    gradients -- dictionary containing gradients
    X -- the input layer of the model
    Y -- The target vector of the model
    epsilon -- allowable error before gradient check fails

    Outputs:
    difference -- difference between approximated backwards propagation and implemented backwards propagation
    """

    # Set-up variables
    parameters_values, _ = dictionary_to_vector(parameters)
    grad = gradients_to_vector(gradients)
    num_parameters = parameters_values.shape[0]
    J_plus = np.zeros((num_parameters, 1))
    J_minus = np.zeros((num_parameters, 1))
    gradapprox = np.zeros((num_parameters, 1))

    # Compute gradapprox
    for i in range(num_parameters):
        # Compute J_plus[i]. Inputs: "parameters_values, epsilon". Output = "J_plus[i]".
        # "_" is used because the function you have to outputs two parameters but we only care about the first one
        ### START CODE HERE ### (approx. 3 lines)
        thetaplus = np.copy(parameters_values)  # Step 1
        thetaplus[i][0] = thetaplus[i][0] + epsilon  # Step 2
        J_plus[i], _ = forward_propagation_n(X, Y, vector_to_dictionary(thetaplus))  # Step 3
        ### END CODE HERE ###

        # Compute J_minus[i]. Inputs: "parameters_values, epsilon". Output = "J_minus[i]".
        ### START CODE HERE ### (approx. 3 lines)
        thetaminus = np.copy(parameters_values)  # Step 1
        thetaminus[i][0] = thetaminus[i][0] - epsilon  # Step 2
        J_minus[i], _ = forward_propagation_n(X, Y, vector_to_dictionary(thetaminus))  # Step 3
        ### END CODE HERE ###

        # Compute gradapprox[i]
        ### START CODE HERE ### (approx. 1 line)
        gradapprox[i] = (J_plus[i] - J_minus[i]) / (2 * epsilon)
        ### END CODE HERE ###

    # Compare gradapprox to backward propagation gradients by computing difference.
    ### START CODE HERE ### (approx. 1 line)
    numerator = np.linalg.norm(grad - gradapprox)  # Step 1'
    denominator = np.linalg.norm(grad) + np.linalg.norm(gradapprox)  # Step 2'
    difference = numerator / denominator  # Step 3'
    ### END CODE HERE ###

    if difference > 2e-7:
        print(
            "\033[93m" + "There is a mistake in the backward propagation! difference = " + str(difference) + "\033[0m")
    else:
        print(
            "\033[92m" + "Your backward propagation works perfectly fine! difference = " + str(difference) + "\033[0m")

    return difference


def update_parameters(parameters, grads, learning_rate):
    """
    Purpose: Updates parameters after forward and backward propagation steps

    Inputs:
    parameters -- dictionary of weights and biases
    grads -- a dictionary of gradients (dW, db, dA)
    X -- the input layer of the model
    learning_rate -- the learning rate of the model (lower is slower but less oscillation)

    Outputs:
    parameters -- updated dictionary of weights and biases
    """

    L = len(parameters) // 2  # number of layers in the neural network

    # Update rule for each parameter. Use a for loop.
    for l in range(L):
        parameters["W" + str(l + 1)] = parameters["W" + str(l + 1)] - learning_rate * grads["dW" + str(l + 1)]
        parameters["b" + str(l + 1)] = parameters["b" + str(l + 1)] - learning_rate * grads["db" + str(l + 1)]
    return parameters


def random_mini_batches(X, Y, mini_batch_size=64):
    """
    Purpose: split X and Y into mini-batches for mini-batched gradient descent

    Inputs:
    X -- the input layer of the model
    Y -- The target vector of the model
    mini_batch_size -- the number of samples to include in each mini batch (generally a power of 2)

    Outputs:
    mini_batches -- a list of tuples of ndarrays containing (Mini batches -> X, Y -> arrays)
    """

    m = X.shape[1]  # number of training examples
    mini_batches = []

    # Step 1: Shuffle (X, Y)
    permutation = list(np.random.permutation(m))
    shuffled_X = X[:, permutation]
    shuffled_Y = Y[:, permutation].reshape((1, m))

    # Step 2: Partition (shuffled_X, shuffled_Y). Minus the end case.
    num_complete_minibatches = math.floor(m / mini_batch_size)
    for k in range(0, num_complete_minibatches):
        mini_batch_X = shuffled_X[:, k * mini_batch_size: (k + 1) * mini_batch_size]
        mini_batch_Y = shuffled_Y[:, k * mini_batch_size: (k + 1) * mini_batch_size]
        mini_batch = (mini_batch_X, mini_batch_Y)
        mini_batches.append(mini_batch)

    # Handling the end case (last mini-batch < mini_batch_size)
    if m % mini_batch_size != 0:
        mini_batch_X = shuffled_X[:, k * mini_batch_size: shuffled_X.shape[1]]
        mini_batch_Y = shuffled_Y[:, k * mini_batch_size: shuffled_Y.shape[1]]
        mini_batch = (mini_batch_X, mini_batch_Y)
        mini_batches.append(mini_batch)

    return mini_batches


def initialize_velocity(parameters):
    """
    Purpose: initialize arrays for momentum optimization

    Inputs:
    parameters -- a dictionary of weights and biases

    Outputs:
    v -- a dictionary of exponentially weighted averages for the gradients of W and b
    """

    L = len(parameters) // 2  # number of layers in the neural networks
    v = {}

    # Initialize velocity
    for l in range(L):
        Wl = parameters["W" + str(l + 1)]
        bl = parameters["b" + str(l + 1)]
        v["dW" + str(l + 1)] = np.zeros((Wl.shape[0], Wl.shape[1]))
        v["db" + str(l + 1)] = np.zeros((bl.shape[0], 1))

    return v


def initialize_adam(parameters):
    """
    Purpose: initialize arrays for Adam optimization

    Inputs:
    parameters -- a dictionary of weights and biases

    Outputs:
    v -- a dictionary of exponentially weighted averages for the gradients of W and b
    s -- a dictionary of exponentially weighted averages for the squared gradients of W and b
    """

    L = len(parameters) // 2  # number of layers in the neural networks
    v = {}
    s = {}

    # Initialize v, s. Input: "parameters". Outputs: "v, s".
    for l in range(L):
        Wl = parameters["W" + str(l + 1)]
        bl = parameters["b" + str(l + 1)]
        v["dW" + str(l + 1)] = np.zeros((Wl.shape[0], Wl.shape[1]))
        v["db" + str(l + 1)] = np.zeros((bl.shape[0], 1))
        s["dW" + str(l + 1)] = np.zeros((Wl.shape[0], Wl.shape[1]))
        s["db" + str(l + 1)] = np.zeros((bl.shape[0], 1))

    return v, s