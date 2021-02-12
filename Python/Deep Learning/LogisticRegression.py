import numpy as np
import matplotlib.pyplot as plt
import h5py
import scipy
import sklearn

from sklearn import datasets
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay


def logistic_regression(X_train, Y_train, X_test, Y_test, num_iterations=2000, learning_rate=0.5):
    # initialize parameters with zeros (≈ 1 line of code)
    w, b = initialize_with_zeros(X_train.shape[0])

    # Gradient descent (≈ 1 line of code)
    parameters, grads, costs = optimize(w, b, X_train, Y_train, num_iterations, learning_rate)

    # Retrieve parameters w and b from dictionary "parameters"
    w = parameters["w"]
    b = parameters["b"]

    # Predict test/train set examples (≈ 2 lines of code)
    Y_prediction_test = predict(w, b, X_test)
    Y_prediction_train = predict(w, b, X_train)

    # Print train/test Errors
    print("train accuracy: {} %".format(100 - np.mean(np.abs(Y_prediction_train - Y_train)) * 100))
    print("test accuracy: {} %".format(100 - np.mean(np.abs(Y_prediction_test - Y_test)) * 100))

    d = {"costs": costs,
         "Y_prediction_test": Y_prediction_test,
         "Y_prediction_train": Y_prediction_train,
         "w": w,
         "b": b,
         "learning_rate": learning_rate,
         "num_iterations": num_iterations}

    return d


def sigmoid(z):
    s = 1 / (1 + np.exp(-1 * z))
    return s


def initialize_with_zeros(dim):
    w = np.zeros((dim, 1))
    b = 0
    return w, b


def propagate(w, b, X, Y):
    m = X.shape[1]
    A = sigmoid(np.dot(w.T, X) + b)
    cost = -1 / m * np.sum(Y * np.log(A) + (1 - Y) * np.log(1 - A))
    dw = 1 / m * np.dot(X, (A - Y).T)
    db = 1 / m * np.sum(A - Y)
    grads = {"dw": dw,
             "db": db}
    cost = np.squeeze(cost)
    return grads, cost


def optimize(w, b, X, Y, num_iter, learning_rate):
    costs = []
    for i in range(num_iter):

        grads, cost = propagate(w, b, X, Y)

        dw = grads["dw"]
        db = grads["db"]

        w = w - learning_rate * dw
        b = b - learning_rate * db

        if i % 100 == 0:
            costs.append(cost)

    params = {"w": w,
              "b": b}

    grads = {"dw": dw,
             "db": db}

    return params, grads, costs


def predict(w, b, X):
    m = X.shape[1]
    Y_prediction = np.zeros((1, m))
    w = w.reshape(X.shape[0], 1)

    A = sigmoid(np.dot(w.T, X) + b)

    for i in range(A.shape[1]):
        if A[0, i] <= 0.5:
            Y_prediction[0, i] = 0
        else:
            Y_prediction[0, i] = 1

    return Y_prediction


if __name__ == '__main__':
    #Input sample data
    X, y = datasets.load_breast_cancer(return_X_y=True)
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.33, random_state = 42)
    d = logistic_regression(X_train.T, y_train.T, X_test.T, y_test.T, num_iterations = 2000, learning_rate = 0.01)
    y_pred_test = d["Y_prediction_test"]
    y_pred_train = d["Y_prediction_train"]
    cm_train = confusion_matrix(y_train.T, np.squeeze(y_pred_train))
    cm_test = confusion_matrix(y_test.T, np.squeeze(y_pred_test))
    disp = ConfusionMatrixDisplay(confusion_matrix=cm_train, display_labels=("Malignant", "Benign"))
    disp.plot(cmap='plasma')
    plt.show()

