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
