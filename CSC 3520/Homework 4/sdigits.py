# svm_demo.py
# Simple demo of a Support Vector Machine (SVM) using scikit-learn.

from sklearn import svm
import tensorflow as tf
import argparse

parser = argparse.ArgumentParser(description="Use an SVM to distinguish between written digits in the MNIST dataset.")
parser.add_argument('-s', '--samples', help='number of training samples to use', default=60000)
parser.add_argument('-c', '--c', help='number of training samples to use', default=1)
parser.add_argument('-k', '--kernel', help='number of training samples to use', default="poly")


def main(args):
    num_train = int(args.samples)
    
	# Load MNIST data
    (x, t), (x_test, t_test) = tf.keras.datasets.mnist.load_data()
    x = x.reshape((60000, 28*28))
    x_test = x_test.reshape((10000, 28*28))

	# Train SVM
    clf = svm.SVC(kernel=args.kernel, C=float(args.c))
    clf.fit(x[:(num_train - 1)], t[:(num_train - 1)])

	# Show results
    acc = clf.score(x_test, t_test)
    print(f'Accuracy for SVM with {num_train} MNIST training samples: {acc}')


if __name__ == "__main__":
	main(parser.parse_args())