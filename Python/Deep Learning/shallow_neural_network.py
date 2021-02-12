import numpy as np
import matplotlib.pyplot as plt
import sklearn

from sklearn import datasets
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay


def nn_model(X, Y, n_h, num_iterations=10000, print_cost=False):
    # This is the main function for the shallow neural network
    # Here, we take in the features and targets, define the size of teh single hidden layer, and compute

    n_x = layer_sizes(X, Y)[0]
    n_y = layer_sizes(X, Y)[1]

    # Initialize parameters
    parameters = initialize_parameters(n_x, n_h, n_y)

    # Loop (gradient descent)

    for i in range(0, num_iterations):

        # Forward propagation. Inputs: "X, parameters". Outputs: "A2, cache".
        A2, cache = forward_propagation(X, parameters)

        # Cost function. Inputs: "A2, Y, parameters". Outputs: "cost".
        cost = compute_cost(A2, Y, parameters)

        # Backpropagation. Inputs: "parameters, cache, X, Y". Outputs: "grads".
        grads = backward_propagation(parameters, cache, X, Y)

        # Gradient descent parameter update. Inputs: "parameters, grads". Outputs: "parameters".
        parameters = update_parameters(parameters, grads)

        # Print the cost every 1000 iterations
        if print_cost and i % 1000 == 0:
            print("Cost after iteration %i: %f" % (i, cost))

    return parameters


def sigmoid(z):
    s = 1 / (1 + np.exp(-1 * z))
    return s


def relu(z):
    return z * (z > 0)


def d_relu(z):
    return z > 0


def layer_sizes(X, Y):
    # Here, we take in the features and targets, and define the sizes of teh layers

    n_x = X.shape[0]  # size of input layer
    n_y = Y.shape[0]  # size of output layer
    return n_x, n_y


def initialize_parameters(n_x, n_h, n_y):
    # Here, the input parameters are:
    # n_x = number of input parameters
    # n_h = size of the shallow neural network (ie number of nodes in single hidden layer)
    # n_y = number of output parameters

    W1 = np.random.randn(n_h, n_x) * 0.001
    b1 = np.zeros((n_h, 1))
    W2 = np.random.randn(n_y, n_h) * 0.001
    b2 = np.zeros((n_y, 1))

    assert (W1.shape == (n_h, n_x))
    assert (b1.shape == (n_h, 1))
    assert (W2.shape == (n_y, n_h))
    assert (b2.shape == (n_y, 1))

    parameters = {"W1": W1,
                  "b1": b1,
                  "W2": W2,
                  "b2": b2}

    return parameters


def forward_propagation(X, parameters):
    # Here we take in our features and the weights and biases for the two layers and compute the activations
    # In this case, we are doing classification between 0 and 1, so use a sigmoid output and tanh hidden

    W1 = parameters["W1"]
    b1 = parameters["b1"]
    W2 = parameters["W2"]
    b2 = parameters["b2"]

    Z1 = np.dot(W1, X) + b1
    A1 = np.tanh(Z1)
    Z2 = np.dot(W2, A1) + b2
    A2 = sigmoid(Z2)

    cache = {"Z1": Z1,
             "A1": A1,
             "Z2": Z2,
             "A2": A2}

    return A2, cache


def compute_cost(A2, Y, parameters):
    # Here, we take in the activation of the output layer, and compute the cost function to determine the accuracy of
    # our model with the current set of weights and biases

    m = Y.shape[1]  # number of example

    # Compute the cross-entropy cost
    logprobs = np.multiply(np.log(A2), Y)
    cost = -1 * np.sum(logprobs)

    cost = float(np.squeeze(cost))  # makes sure cost is the dimension we expect.
    # E.g., turns [[17]] into 17
    assert (isinstance(cost, float))

    return cost


def backward_propagation(parameters, cache, X, Y):
    # Here, we take in the weights, biases, activation functions, features and targets, and compute the gradients

    m = X.shape[1]

    # First, retrieve W1 and W2 from the dictionary "parameters".
    W1 = parameters["W1"]
    W2 = parameters["W2"]

    # Retrieve also A1 and A2 from dictionary "cache".
    A1 = cache["A1"]
    A2 = cache["A2"]

    # Backward propagation: calculate dW1, db1, dW2, db2.
    dZ2 = A2 - Y
    dW2 = 1 / m * np.dot(dZ2, A1.T)
    db2 = 1 / m * np.sum(dZ2, axis=1, keepdims=True)
    dZ1 = np.dot(W2.T, dZ2) * (1 - np.power(A1, 2))
    #dZ1 = np.dot(W2.T, dZ2) * d_relu(A1)
    dW1 = 1 / m * np.dot(dZ1, X.T)
    db1 = 1 / m * np.sum(dZ1, axis=1, keepdims=True)

    grads = {"dW1": dW1,
             "db1": db1,
             "dW2": dW2,
             "db2": db2}

    return grads


def update_parameters(parameters, grads, learning_rate=0.0001):
    # Here, we use the calculated gradients to determine the new weights and biases using x2 = x1 - learning_rate * dx1

    # Retrieve each parameter from the dictionary "parameters"
    W1 = parameters["W1"]
    b1 = parameters["b1"]
    W2 = parameters["W2"]
    b2 = parameters["b2"]

    # Retrieve each gradient from the dictionary "grads"
    dW1 = grads["dW1"]
    db1 = grads["db1"]
    dW2 = grads["dW2"]
    db2 = grads["db2"]

    # Update rule for each parameter
    W1 = W1 - learning_rate * dW1
    b1 = b1 - learning_rate * db1
    W2 = W2 - learning_rate * dW2
    b2 = b2 - learning_rate * db2

    parameters = {"W1": W1,
                  "b1": b1,
                  "W2": W2,
                  "b2": b2}

    return parameters


def predict(parameters, X):
    # Here, we take in the weights and biases, as well as the features, and calculate predicted values

    # Computes probabilities using forward propagation, and classifies to 0/1 using 0.5 as the threshold.
    A2, cache = forward_propagation(X, parameters)
    predictions = (A2 > 0.5)

    return predictions


if __name__ == '__main__':
    # Input sample data
    X, y = datasets.load_breast_cancer(return_X_y=True)
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.33)
    parameters = nn_model(X_train.T, np.reshape(y_train, ((1, -1))), n_h = 4, num_iterations = 100000, print_cost=True)
    y_pred_test = predict(parameters, X_test.T)
    y_pred_train = predict(parameters, X_train.T)
    cm_train = confusion_matrix(y_train.T, np.squeeze(y_pred_train))
    cm_test = confusion_matrix(y_test.T, np.squeeze(y_pred_test))
    disp = ConfusionMatrixDisplay(confusion_matrix=cm_test, display_labels=("Malignant", "Benign"))
    disp.plot(cmap='plasma')
    plt.show()
