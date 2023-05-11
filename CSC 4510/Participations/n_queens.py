# n_queens.py
"""Solve the n-queens problem using a genetic algorithm."""

from checkerboard import *
from argparse import ArgumentParser
import numpy as np


parser = ArgumentParser(description = "Solve the n-queens problem through a genetic algorithm.")
parser.add_argument('-n', '--number_queens',
                    help = 'Number of queens to solve for, defaults to 8',
                    default = 8,
                    type = int)
parser.add_argument('-k', '--k',
                    help = 'Number of individuals to pick during a tournament, defaults to 5',
                    default = 5,
                    type = int)
parser.add_argument('-cr', '--crossover_rate',
                    help = 'The rate at which parents should crossover, defaults to 0.8',
                    default = 0.8,
                    type = float,
                    choices = range(0, 1.00001))
parser.add_argument('-mr', '--mutation_rate',
                    help = 'The rate at which offspring should mutate, defaults to 0.1',
                    default = 0.1,
                    type = float,
                    choices = range(0, 1.00001))
parser.add_argument('-p', '--population_size',
                    help = 'Number of potential solutions to be in our population, defaults to 100',
                    default = 100,
                    type = int)
parser.add_argument('-o', '--offspring_size',
                    help = 'Number of offspring to produce, defaults to 100',
                    default = 100,
                    type = int)
parser.add_argument('-t', '--termination_generation',
                    help = 'Number of generations to terminate at if a solution is not found by then, defaults to 100',
                    default = 100,
                    type = int)
parser.add_argument('-d', '--display',
                    help = 'Flag for whether or not to display every generation',
                    action='store_true')
parser.add_argument('-v', '--verbose',
                    help = 'Flag for whether or not to print information about every generation',
                    action='store_true')


def score(locations: np.ndarray, strict: bool = False) -> int:
    """Takes in the locations of queens on a chess board and whether or not to be strict with scoring (strict means non-pairwise)

    Args:
        locations (np.ndarray): Array representing the locations of queens on a chess board
        strict (bool): Boolean value representing whether or not to score strictly (strictly meaning non-pairwise)

    Returns:
        int: Number of violations
    """
    
    violations = 0
    if strict:
        # for every queen, loop over every other queen
        for i in range(0, len(locations)):
            for j in range(0, len(locations)):
                if i == j: # don't consider the same queen
                    continue
                if locations[i] == locations[j] or abs(i - j) == abs(locations[i] - locations[j]): # if they're in the same column OR on the same diagonal
                    violations += 1 # add a violation and break (since this queen is in check)
                    break
    else:
        # for every queen, loop over every other queen
        for i in range(0, len(locations)):
            for j in range(0, len(locations)):
                if i == j: # don't consider the same queen
                    continue
                if locations[i] == locations[j] or abs(i - j) == abs(locations[i] - locations[j]): # if they're in the same column OR on the same diagonal
                    violations += 1 # add a violation
                    continue
    return violations


def initialize(n: int, population_size: int) -> np.ndarray:
    """Takes in the number of queens and the population size to create an array of random integers between 0 and n - 1

    Args:
        n (int): Number of queens
        population_size (int): Size of the population

    Returns:
        np.ndarray: population_size X n array of random integers between 0 and n - 1 (inclusive)
    """
    
    return np.random.randint(0, n, (population_size, n)) # create an array of random integers from 0 to the number of Queens of size (population, number queens)


def parent_selection(population: np.ndarray, population_size: int, offspring_size: int, k: int) -> np.ndarray:
    """Selects the parents from the population via tournament selection

    Args:
        population (np.ndarray): Array representing the population
        population_size (int): Size of the population
        offspring_size (int): Number of offspring to produce
        k (int): Number of individuals to consider in each "round" of the "tournament"

    Returns:
        np.ndarray: Array representing the parents selected from the population
    """
    
    scores = [score(individual)/2 for individual in population] # score the entire population
    population = population[np.argsort(scores)] # sort the population based on their scores
    choices = np.random.randint(0, population_size, (offspring_size, k)) # randomly select 5 individuals for each individual in the population
    choices = np.sort([np.min(choice) for choice in choices]) # choose the lowest (thus, best scored since sorted) for each and sort it
    parents = population[choices[:population_size]] # the parents are the population at the choices
    return parents # return the parents, which should be sorted


def survivor_selection(population: np.ndarray, mutants: np.ndarray, population_size: int, method: str) -> tuple[np.ndarray, np.ndarray]:
    """Selects the survivors from the candidates (mutants and, if method is not age-based, population)

    Args:
        population (np.ndarray): Array representing the population
        mutants (np.ndarray): Array representing the mutated children
        population_size (int): Size of the population
        method (str): Which method to use for survivor selection

    Returns:
        np.ndarray: Array representing the survivors selected from the candidates
        np.ndarray: Array representing the scores of the selected survivors
    """
    
    candidates = mutants # assign the mutants to the candidates
    if method != "age-based":
        candidates = np.r_[population, mutants] # if the seleciton method isn't age-based, then add the population
    scores = np.asarray([score(candidate)/2 for candidate in candidates]) # score all of the candidates
    sort = np.argsort(scores) # find the indices to sort the scores
    candidates = candidates[sort] # sort the candidates by the previous indices
    scores = scores[sort] # sort the scores by the same previous indices
    return candidates[:population_size], scores[:population_size] # return the first population_size candidates and their scores


def uniform_crossover(parents: np.ndarray, crossover_rate: float, n: int, population_size: int) -> np.ndarray:
    """Performs uniform crossover on the children and return the mutants

    Args:
        parents (np.ndarray): Array representing the parents
        crossover_rate (float): Rate at which to cross children over
        n (int): Number of queens
        population_size (int): Size of the population

    Returns:
        np.ndarray: Array representing the children after crossover
    """
    
    permutations = np.array([np.random.permutation(population_size) for _ in range(n)]).T # Generate n permutations of the population size and transpose it so it works better with the way our data is formatted
    children = parents.copy() # copy the parents to the children
    mask = np.random.rand(n) <= crossover_rate # create a 1D mask of size n with the crossover rate determining whether or not it will be True
    children[:, mask] = parents[permutations[:, mask], mask] # Pass mask over children array and where mask is true, assign children to the ith parent at mask where i is determined by permutations at mask
    return children # return the crossed over children


def mutate_random_reset(population: np.ndarray, mutation_rate: float, n: int, population_size: int) -> np.ndarray:
    """Performs random resetting mutation on the population

    Args:
        population (np.ndarray): Array representing the population
        mutation_rate (float): Rate at which to mutate the population
        n (int): Number of queens
        population_size (int): Size of the population

    Returns:
        np.ndarray: Array representing the mutated population
    """
    
    mask = np.random.random(size=population.shape) <= mutation_rate # create a 1D boolean array mask
    np.putmask(population, mask, np.random.randint(0, n, population_size)) # put the mask on the population array, where the mask is true reset the integer to a random one
    return population # return the masked array


def main(args):
    # Initialize the variables
    # Get variables from the ArgumentParser
    n = args.number_queens
    k = args.k
    crossover_rate = args.crossover_rate
    mutation_rate = args.mutation_rate
    population_size = args.population_size
    offspring_size = args.offspring_size
    termination = args.termination_generation
    display = args.display
    verbose = args.verbose
    size = 600 / n
    # Random variables mostly not used rn for if I decide to expand this and make it even cooler
    representation = int
    crossover_method = "uniform"
    mutation_method = "random resetting"
    parent_selection_method = "tournament"
    survivor_selection_method = "age-based"
    # Creating the GUI and initializing an empty array of the circles representing the queens
    if display:
        gui = draw(n, n, size)
    queen_circles = []
    # Initialize population
    population = initialize(n, population_size)
    
    # Main loop
    for i in range(1, termination + 1):
        if verbose:
            print(f'=== Generation {i} ===')
        # Call functions in order
        parents = parent_selection(population, population_size, offspring_size, k)
        children = uniform_crossover(parents, crossover_rate, n, population_size)
        mutants = mutate_random_reset(children, mutation_rate, n, population_size)
        population, scores = survivor_selection(population, mutants, population_size, survivor_selection_method)
        # If the score is 0, print information that we found the solution and show it on the board
        if scores[0] == 0:
            print(f'Solution found on Generation {i}!\nSolution: {population[0]}.\nPress "Esc" to close the window...')
            if not display:
                gui = draw(n, n, size)
            add(gui, population[0], size=size)
            while True:
                key = gui.checkKey()
                if key == 'Escape':
                    break  # exit the loop when 'Esc' is pressed
            break
        # Otherwise, print information about the current generation (best score and best locations) and show it on the board
        if verbose:
            print(f'Best for Generation {i}:\nScore - {scores[0]}.\nLocations - {population[0]}.\nPress "Esc" to continue...\n')
        if display:
            queen_circles = add(gui, population[0], size=size)
            # Wait for user input to close the GUI
            while True:
                key = gui.checkKey()
                if key == 'Escape':
                    break  # exit the loop when 'Esc' is pressed
            remove(queen_circles)


if __name__ == '__main__':
    main(parser.parse_args())