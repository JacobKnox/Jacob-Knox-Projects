import numpy as np
import matplotlib.pyplot as plt
from sklearn.utils.extmath import cartesian

def show_boundaries(in_weights, hidden_weights):
    # Get two arrays of every value between -5.0 and 5.0 (inclusive) with a step of 0.2
    x1 = np.arange(-5.0, 5.2, step = 0.2, dtype=float)
    x2 = np.arange(-5.0, 5.2, step = 0.2, dtype=float)
    # Generate all possible cartesian coordinates of the two
    x_main = cartesian((x1, x2))
    # Put an extra column of ones for the bias
    x = np.column_stack((np.ones(len(x_main)), x_main))
    # Push them through to the hidden layer
    z = 1.716 * np.tanh((2/3) * np.matmul(x, in_weights))
    # Add a bias to the hidden layer
    z = np.column_stack((np.ones(len(z)), z))
    # Push them through to the output layer
    output = 1.716 * np.tanh((2/3) * np.matmul(z, hidden_weights))
    # Create a mask from the output layer based on whether or not the value is greater than 0
    mask = output > 0
    # Generate the figure where values greater than 0 are a green circle and values less than or equal to 0 are a red x
    plt.figure()
    plt.plot(x_main[mask, 0], x_main[mask, 1], 'go', x_main[~mask, 0], x_main[~mask, 1], 'rx')
    plt.show()

def main():
    # Manually assign the first set of weights
    in_weights = [
        [0.5, -0.5],
        [0.3, -0.4],
        [-0.1, 1.0]
    ]
    hidden_weights = [1.0, -2.0, 0.5]
    # Call the function
    show_boundaries(in_weights, hidden_weights)
    # Manually assign the second set of weights
    in_weights = [
        [-1.0, 1.0],
        [-0.5, 1.5],
        [1.5, -0.5]
    ]
    hidden_weights = [0.5, -1.0, 1.0]
    # Call the function
    show_boundaries(in_weights, hidden_weights)
    
main()