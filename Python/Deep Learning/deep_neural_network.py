import numpy as np
import matplotlib.pyplot as plt
from sklearn import datasets
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay
from sklearn.model_selection import train_test_split


def L_layer_model(X, Y, layer_dims, learning_rate=0.0075, num_iterations=3000, print_cost=False):  # lr was 0.009
    # Inputs:
    # X -- matrix of features
    # Y -- vector of targets
    # layer_dims -- vector of layer sizes
    # learning rate -- hyperparameter to decide how aggressive fo steps to take
    # num_iterations -- number of iterations to perform
    # print_cost -- whether to print the cost every 100 iterations or not
    # Outputs:
    # parameters -- new parameters based on neural network

    costs = []  # keep track of cost

    # Parameters initialization.
    parameters = initialize_parameters(layer_dims)

    # Loop (gradient descent)
    for i in range(0, num_iterations):

        # Forward propagation: [LINEAR -> RELU]*(L-1) -> LINEAR -> SIGMOID.
        AL, caches = L_model_forward(X, parameters)

        # Compute cost.
        cost = compute_cost(AL, Y)

        # Backward propagation.
        grads = L_model_backward(AL, Y, caches)

        # Update parameters.
        parameters = update_parameters(parameters, grads, learning_rate)

        # Print the cost every 100 training example
        if print_cost and i % 100 == 0:
            print("Cost after iteration %i: %f" % (i, cost))
        if print_cost and i % 100 == 0:
            costs.append(cost)

    # plot the cost
    plt.plot(np.squeeze(costs))
    plt.ylabel('cost')
    plt.xlabel('iterations (per hundreds)')
    plt.title("Learning rate =" + str(learning_rate))
    plt.show()

    return parameters


def initialize_parameters(layer_dims):
    # Here, we take in our layer dimensions as an array, and return a dictionary of initialized weights and biases
    # Layer_dims contains:
    # n_1: Size of input layer (number of features)
    # n_2...n_L-1: size of hidden layers (number of nodes for each layer)
    # n_L: size of output layer (number of targets)

    parameters = {}
    L = len(layer_dims)  # number of layers in the network

    # The dimensions of the weights and biases are:
    # W_n = n_n X n_n-1 matrix
    # b_n = n_n X 1 matrix
    for l in range(1, L):
        parameters['W' + str(l)] = np.random.randn(layer_dims[l], layer_dims[l - 1]) * 0.01
        parameters['b' + str(l)] = np.zeros((layer_dims[l], 1))

        assert (parameters['W' + str(l)].shape == (layer_dims[l], layer_dims[l - 1]))
        assert (parameters['b' + str(l)].shape == (layer_dims[l], 1))

    return parameters


def linear_forward(A, W, b):
    # Inputs: Activation matrix, weights and biases for one layer
    # Outputs: The pre-activation parameter (Z) as well as the input to the function

    Z = np.dot(W, A) + b

    assert (Z.shape == (W.shape[0], A.shape[1]))
    cache = (A, W, b)

    return Z, cache


def linear_activation_forward(A_prev, W, b, activation):
    # Inputs: Previous layers activation matrix, weights and biases for current layer, type of layer activation as
    # string.
    # Outputs: Current layers activation matrix, the entire input (A_prev, W, b; in linear cache),

    if activation == "sigmoid":
        Z, linear_cache = linear_forward(A_prev, W, b)
        A, activation_cache = sigmoid(Z)

    elif activation == "relu":
        Z, linear_cache = linear_forward(A_prev, W, b)
        A, activation_cache = relu(Z)

    assert (A.shape == (W.shape[0], A_prev.shape[1]))
    cache = (linear_cache, activation_cache)

    return A, cache


def L_model_forward(X, parameters):
    # Inputs: Feature matrix, dictionary of weights and biases
    # Outputs: Final activation layer matrix and a dictionary of all layer caches (linear and activation)
    # As a note, inside the cache, we will have Z, A, W, and b cached for each layer

    caches = []
    A = X
    L = len(parameters) // 2  # number of layers in the neural network

    # Implement [LINEAR -> RELU]*(L-1). Add "cache" to the "caches" list.
    for l in range(1, L):
        A_prev = A
        A, cache = linear_activation_forward(A_prev, parameters["W" + str(l)], parameters["b" + str(l)], "relu")
        caches.append(cache)

    # Implement LINEAR -> SIGMOID. Add "cache" to the "caches" list.
    AL, cache = linear_activation_forward(A, parameters["W" + str(L)], parameters["b" + str(L)], "sigmoid")
    caches.append(cache)

    assert (AL.shape == (1, X.shape[1]))

    return AL, caches


def compute_cost(AL, Y):
    # Inputs:
    # AL -- Activation layer matrix for the output layer
    # Y -- Target layer
    # Outputs:
    # cost -- single number representing the total log loss of the neural network

    m = Y.shape[1]

    # Compute loss from aL and y.
    cost = -1 / m * np.sum(np.multiply(np.log(AL), Y) + (1 - Y) * np.log(1 - AL))

    cost = np.squeeze(cost)  # To make sure your cost's shape is what we expect (e.g. this turns [[17]] into 17).
    assert (cost.shape == ())

    return cost


def linear_backward(dZ, cache):
    # Inputs:
    # dZ -- Gradient of the cost function with respect to Z
    # cache -- Cache of A_prev, W, b from forward propagation to determine derivatives
    # Outputs:
    # Derivatives -- derivatives of the cost function with respect to A_prev, W, and b

    A_prev, W, b = cache
    m = A_prev.shape[1]

    dW = 1 / m * np.dot(dZ, A_prev.T)
    db = 1 / m * np.sum(dZ, axis=1, keepdims=True)
    dA_prev = np.dot(W.T, dZ)


    assert (dA_prev.shape == A_prev.shape)
    assert (dW.shape == W.shape)
    assert (db.shape == b.shape)

    return dA_prev, dW, db


def linear_activation_backward(dA, cache, activation):
    # Inputs:
    # dA -- Gradient of the cost function with respect to A
    # cache -- Tuple of linear and activation caches (linear containing A_prev, W, b; activation containing Z)
    # activation -- string representing the type of activation (relu or sigmoid)
    # Outputs:
    # Derivatives -- derivatives of the cost function with respect to A_prev, W, and b

    linear_cache, activation_cache = cache

    if activation == "relu":
        dZ = relu_backward(dA, cache[1])
        dA_prev, dW, db = linear_backward(dZ, linear_cache)

    elif activation == "sigmoid":
        dZ = sigmoid_backward(dA, cache[1])
        dA_prev, dW, db = linear_backward(dZ, linear_cache)

    return dA_prev, dW, db


def L_model_backward(AL, Y, caches):
    # Inputs:
    # AL -- Activation matrix of output layer
    # Y -- Target vector
    # caches -- array of tuples, each entry caches[n] containing the linear and activation cache for layer n
    # Outputs:
    # grads -- gradients (dA, dW, db) for each layer of the neural net

    grads = {}
    L = len(caches)  # the number of layers
    m = AL.shape[1]
    Y = Y.reshape(AL.shape)  # after this line, Y is the same shape as AL

    # Initializing the backpropagation
    dAL = - (np.divide(Y, AL) - np.divide(1 - Y, 1 - AL))

    # Lth layer (SIGMOID -> LINEAR) gradients. Inputs: "dAL, current_cache". Outputs: "grads["dAL-1"], grads["dWL"], grads["dbL"]
    current_cache = caches[L - 1]
    grads["dA" + str(L - 1)], grads["dW" + str(L)], grads["db" + str(L)] = linear_activation_backward(dAL,
                                                                                                      current_cache,
                                                                                                      "sigmoid")

    # Loop from l=L-2 to l=0
    for l in reversed(range(L - 1)):
        # lth layer: (RELU -> LINEAR) gradients.
        # Inputs: "grads["dA" + str(l + 1)], current_cache". Outputs: "grads["dA" + str(l)] , grads["dW" + str(l + 1)] , grads["db" + str(l + 1)]
        current_cache = caches[l]
        dA_prev_temp, dW_temp, db_temp = linear_activation_backward(grads["dA" + str(l + 1)], current_cache, "relu")
        grads["dA" + str(l)] = dA_prev_temp
        grads["dW" + str(l + 1)] = dW_temp
        grads["db" + str(l + 1)] = db_temp

    return grads


def update_parameters(parameters, grads, learning_rate):
    # Inputs:
    # parameters -- dictionary of weights and biases for each layer
    # grads -- dictionary of gradients for each layer
    # learning rate -- hyperparameter to decide how aggressive fo steps to take
    # Outputs:
    # parameters -- new parameters based on gradient descent

    L = len(parameters) // 2  # number of layers in the neural network

    # Update rule for each parameter. Use a for loop.
    for l in range(L):
        parameters["W" + str(l + 1)] = parameters["W" + str(l + 1)] - learning_rate * grads["dW" + str(l + 1)]
        parameters["b" + str(l + 1)] = parameters["b" + str(l + 1)] - learning_rate * grads["db" + str(l + 1)]
    return parameters


def predict(parameters, X):
    # Here, we take in the weights and biases, as well as the features, and calculate predicted values

    # Computes probabilities using forward propagation, and classifies to 0/1 using 0.5 as the threshold.
    AL, caches = L_model_forward(X, parameters)
    predictions = (AL > 0.5)

    return predictions


def sigmoid(z):
    cache = z
    s = 1 / (1 + np.exp(-1 * z))
    return s, cache


def relu(z):
    cache = z
    r = z * (z > 0)
    return r, cache


def sigmoid_backward(dA, cache):
    z = cache
    s, cache = sigmoid(z)
    ds = dA * s * (1 - s)
    return ds


def relu_backward(dA, cache):
    z = cache
    dr = dA * (z > 0)
    return dr


if __name__ == '__main__':
    # Input sample data
    X, y = datasets.load_breast_cancer(return_X_y=True)
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.33)
    layer_dims = [X_train.shape[1], 20, 13, 8, 1]
    parameters = L_layer_model(X_train.T, np.reshape(y_train, ((1, -1))), layer_dims, learning_rate=0.002, num_iterations=100000, print_cost=True)
    y_pred_test = predict(parameters, X_test.T)
    y_pred_train = predict(parameters, X_train.T)
    cm_train = confusion_matrix(y_train.T, np.squeeze(y_pred_train))
    cm_test = confusion_matrix(y_test.T, np.squeeze(y_pred_test))
    disp = ConfusionMatrixDisplay(confusion_matrix=cm_test, display_labels=("Malignant", "Benign"))
    disp.plot(cmap='viridis')
    plt.show()
