# disney.py
# Plan a trip to Walt Disney World's Magic Kingdom theme park using a genetic algorithm.


import math
import csv
import numpy as np
import time
import os
from os import path
from graphics import *
import argparse
from argparse import ArgumentParser
import pdb


parser = ArgumentParser(description = "Run a genetic algorithm to determine the path with the most fun at Walt Disney World's Magic Kingdom.")
parser.add_argument('-k', '--k',
                    help = 'Number of individuals to pick during a tournament, defaults to 5',
                    default = 5,
                    type = int)
parser.add_argument('-cr', '--crossover_rate',
                    help = 'The rate at which parents should crossover, defaults to 0.8',
                    default = 0.8,
                    type = float)
parser.add_argument('-mr', '--mutation_rate',
                    help = 'The rate at which offspring should mutate, defaults to 0.35',
                    default = 0.35,
                    type = float)
parser.add_argument('-p', '--population_size',
                    help = 'Number of potential solutions to be in our population, defaults to 100',
                    default = 100,
                    type = int)
parser.add_argument('-o', '--offspring_size',
                    help = 'Number of offspring to produce, defaults to 100',
                    default = 100,
                    type = int)
parser.add_argument('-tg', '--termination_generation',
                    help = 'Number of generations to terminate at, defaults to 100',
                    default = 100,
                    type = int)
parser.add_argument('-e', '--early_stopping',
                    help = 'Number of generations to stop after if no changes are made to the best path, defaults to 10',
                    default = 10,
                    type = int)
parser.add_argument('-t', '--time',
                    help = 'How many hours you will be in the park, defaults to 10',
                    default = 10,
                    type = int)
parser.add_argument('-v', '--verbose',
                    help = 'Whether or not to display the best from each iteration and stats related to it',
                    default = False,
                    action = argparse.BooleanOptionalAction)
parser.add_argument('-d', '--diminish',
                    help = 'Whether or not to implement the law of diminishing returns',
                    default = True,
                    action = argparse.BooleanOptionalAction)


ROOT = path.dirname(__file__)
FILE_IMG = 'magickingdom.png'
FILE_LOC = 'ridelocations.txt'
FILE_RIDE = 'ridetimes.txt'
FILE_WAIT = 'waittimes.txt'
FILE_FUN = 'ridefun.txt'
COLOR_RIDE = '#3e50c4'
COLOR_PATH = '#3e50c4'
PAUSE = 0.02  # increase to show planned route with longer time delay


def main(args):
    global locations, times, wait, fun, hours, diminish # making these global, because I was having to pass them to like 3+ functions and it was just gross
    
    # Load data from files
    locations = readlocations(os.path.join(ROOT, FILE_LOC))
    times = readridetimes(os.path.join(ROOT, FILE_RIDE))
    wait = readwaittimes(os.path.join(ROOT, FILE_WAIT))
    fun = readfun(os.path.join(ROOT, FILE_FUN))
    gui = makegui(os.path.join(ROOT, FILE_IMG))
    addrides(gui, locations)

    # Run genetic algorithm
    # ***EDIT CODE HERE***
    # Yep, random variables that do things (change to argparse?)
    rides = list(fun.keys())
    hours = args.time
    max_rides = math.ceil(hours * 60 / min(times.values()))
    termination = args.termination_generation
    population_size = args.population_size
    offspring_size = args.offspring_size
    k = args.k
    mutation_rate = args.mutation_rate
    crossover_rate = args.crossover_rate
    best_path = np.random.choice(rides, max_rides)
    best_overall_score = 0
    best_overall_time = 0
    counter = 0
    early_stopping = args.early_stopping
    verbose = args.verbose
    diminish = args.diminish
    
    # Initialize the population
    population = initialize(rides, max_rides, population_size)
    # Loop termination number of times
    for i in range(1, termination + 1):
        if verbose:
            print(f'=== Generation {i} ===')
        # Call functions in order
        parents = parent_selection(population, population_size, offspring_size, k)
        children = uniform_crossover(parents, crossover_rate, max_rides, population_size)
        mutants = inversion_mutation(children, mutation_rate)
        population, _ = survivor_selection(population, mutants, population_size)
        # Find information about the best path
        best_score, best_num_rides, best_time = fitness(population[0], True)
        # If it's better than the current overall best, then update the variables
        if best_score > best_overall_score:
            best_path = population[0][:best_num_rides]
            best_overall_score = best_score
            best_overall_time = best_time
        # If the best has not changed at all, then don't show it
        elif np.array_equal(best_path, population[0][:best_num_rides]):
            counter += 1
            if counter == early_stopping:
                if verbose:
                    print(f"Stopping early since {early_stopping} generations went by without change to the best path.\n")
                break
            if verbose:
                print("Skipping showing best from current iteration, because it is the same as the previous iteration.\n")
            continue
        counter = 0
        # Clear the map
        clearroute(gui)
        if verbose:
            # Print the score
            print(f'Best Score: {best_score}\nTime: {best_time}\nRoute:')
            # Add the new path to the map
            addroute(gui, locations, population[0][:best_num_rides])
            print("Press Esc to continue to the next iteration...\n")
            # Keep the current iteration displayed until the user presses Esc
            while True:
                key = gui.checkKey()
                if key == 'Escape':
                    break
    
    # Clear the map, just in case
    clearroute(gui)
    # Print information about the overall best path and show it on the map
    print(f'The algorithm has terminated.\nBest Score Found: {best_overall_score}\nTime: {best_overall_time}\nRoute:')
    addroute(gui, locations, best_path)
    print("Press Esc to exit the program...\n")
    # Keep the program running until the user presses Esc
    while True:
        key = gui.checkKey()
        if key == 'Escape':
            break


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
    
    scores = np.asarray([fitness(individual) for individual in population]) # score the entire population
    population = population[(-scores).argsort()] # sort the population based on their scores
    choices = np.random.randint(0, population_size, (offspring_size, k)) # randomly select 5 individuals for each individual in the population
    choices = np.sort([np.min(choice) for choice in choices]) # choose the lowest (thus, best scored since sorted) for each and sort it
    parents = population[choices[:population_size]] # the parents are the population at the choices
    return parents # return the parents, which should be sorted


def survivor_selection(population: np.ndarray, mutants: np.ndarray, population_size: int) -> tuple[np.ndarray, np.ndarray]:
    """Selects the survivors from the candidates (mutants and population) using fitness-based replacement

    Args:
        population (np.ndarray): Array representing the population
        mutants (np.ndarray): Array representing the mutated children
        population_size (int): Size of the population

    Returns:
        np.ndarray: Array representing the survivors selected from the candidates
        np.ndarray: Array representing the scores of the selected survivors
    """
    
    candidates = np.r_[population, mutants] # if the seleciton method isn't age-based, then add the population
    scores = np.asarray([fitness(candidate) for candidate in candidates]) # score all of the candidates
    sort = (-scores).argsort() # find the indices to sort the scores
    candidates = candidates[sort] # sort the candidates by the previous indices
    scores = scores[sort] # sort the scores by the same previous indices
    return candidates[:population_size], scores[:population_size] # return the first population_size candidates and their scores


def inversion_mutation(population: np.ndarray, mutation_rate: float) -> np.ndarray:
    """Performs inversion mutation on the population

    Args:
        population (np.ndarray): Array representing the population
        mutation_rate (float): Rate at which to mutate the population

    Returns:
        np.ndarray: Array representing the mutated population
    """
    
    for individual in population: # loop over every individual in the population
        if mutation_rate >= np.random.random(): # decide whether or not to mutate randomly based on the mutation rate
            start = np.random.randint(0, len(individual)) # generate a random starting index
            end = np.random.randint(start, len(individual)) # generate a random ending index that is greater than or equal to the starting index
            individual[start:end] = np.flip(individual[start:end]) # reverse/invert the genes
    return population # return the mutated population


def uniform_crossover(parents: np.ndarray, crossover_rate: float, max_rides: int, population_size: int) -> np.ndarray:
    """Performs uniform crossover on the children and return the mutants

    Args:
        parents (np.ndarray): Array representing the parents
        crossover_rate (float): Rate at which to cross children over
        max_rides (int): Maximum number of rides kind of feasible
        population_size (int): Size of the population

    Returns:
        np.ndarray: Array representing the children after crossover
    """
    
    permutations = np.array([np.random.permutation(population_size) for _ in range(max_rides)]).T # Generate n permutations of the population size and transpose it so it works better with the way our data is formatted
    children = parents.copy() # copy the parents to the children
    mask = np.random.rand(max_rides) <= crossover_rate # create a 1D mask of size n with the crossover rate determining whether or not it will be True
    children[:, mask] = parents[permutations[:, mask], mask] # Pass mask over children array and where mask is true, assign children to the ith parent at mask where i is determined by permutations at mask
    return children # return the crossed over children


def fitness(candidate: np.ndarray, return_others: bool = False) -> int | tuple[int, int]:
    """Calculates the fitness of a potential solution

    Args:
        candidate (np.ndarray): Array of locations representing the candidate solution
        return_count (bool): Whether or not to return ride count

    Returns:
        int: Total fun experienced with this solution
        int: Number of rides you could ride
        int: Total time spent
    """
    
    total_time = calc_time("Entrance", candidate[0]) # Initialize the total time with the Entrance to the first ride
    total_fun = fun[candidate[0]] # Initialize the total fun to the first ride
    counter = 0 # Initialize counter to 0
    visit_dict = dict(zip(fun.keys(), np.zeros(len(fun.keys())))) # Initialize a dictionary of the locations with 0 representing the number of times they've been visited
    visit_since_dict = dict(zip(fun.keys(), np.zeros(len(fun.keys())))) # Initialize a dictionary of the locations with 0 represenitng the number of other rides visited since the last time this ride was visited
    visit_dict[candidate[0]] += 1 # Add 1 visit to the first candidate
    while total_time < (hours * 60) and counter < (len(candidate) - 1): # While the total time does not exceed 10 hours and we don't run through all rides
        counter += 1 # Add to the counter
        total_time += calc_time(candidate[counter - 1], candidate[counter]) # Calculate the time between rides and to ride the next ride
        total_fun += fun[candidate[counter]] # Add to the total fun score
        
        if diminish:
            # This line attempts to implement the law of diminishing returns, subtracts anywhere from 0 to ln(# visits * fun)
            total_fun -= max(math.ceil(np.log(max(visit_dict[candidate[counter]] * fun[candidate[counter]], 1)) - visit_since_dict[candidate[counter]] * 0.5), 0)
        
        visit_dict[candidate[counter]] += 1 # Add to the location's visits
        visit_since_dict[candidate[counter]] = 0 # Reset the vists since to 0
        new_visit_since = list(visit_since_dict.values()) + [1 if (value > 0 and key != candidate[counter]) else 0 for key, value in visit_dict.items()] # Add to the visit since values
        visit_since_dict = dict(zip(fun.keys(), new_visit_since)) # Update the dictionary
    if return_others: # If the return_count flag is set to true, then return the ride counter too
        return total_fun, counter, total_time # Return the total fun score, ride counter, AN total time
    else:
        return total_fun # Return the total fun score


def initialize(rides: list, max_rides: int, population_size: int) -> np.ndarray:
    """Takes in the rides, max number of rides, and population size to create a selection of the rides

    Args:
        rides (list): List of ride names
        max_rides (int): Maximum number of rides kind of feasible
        population_size (int): Size of the population

    Returns:
        np.ndarray: population_size X max_rides array of rides
    """
    
    return np.random.choice(rides, (population_size, max_rides)) # Generate and return an array of size population_size X max_rides of random choices of the rides


def calc_time(start_loc: str, end_loc: str) -> float:
    """Calculates the time to travel from the start location to the end location and ride the ride there

    Args:
        start_loc (str): Name of the starting location
        end_loc (str): Name of the ending location

    Returns:
        float: Time to travel from one location to another and ride the ride there
    """
    
    distance = math.sqrt((locations[end_loc][0] - locations[start_loc][0])**2 + (locations[end_loc][1] - locations[start_loc][1])**2) # Calculate the distance between the locations using Euclidean geometry
    time = 0.015 * distance + wait[end_loc] + times[end_loc] # Calculate the total time
    return time # Return the time


#########################################################
#                                                       #
# Eicholtz's provided functions; don't touch below here #
#                                                       #
#########################################################


def addpath(gui, a, b):
    '''Add a path from point a to point b on the map.'''
    line = Line(Point(a[0], a[1]), Point(b[0], b[1]))
    line.setWidth(4)
    line.setOutline(COLOR_PATH)
    line.draw(gui)


def addrides(gui, locations):
    '''Add markers for relevant rides on the gui using ride locations.'''
    for ride, xy in locations.items():
        # print(ride, xy)
        circ = Circle(Point(xy[0], xy[1]), 8)
        circ.setOutline(COLOR_RIDE)
        circ.setWidth(4)
        circ.draw(gui)


def addroute(gui, locations, plan):
    '''Display the planned route on the map, where the plan is a list of the rides to visit
    sequentially. The first and last location in the plan must be 'Entrance'; if it is not,
    the code adds it to the plan automatically (i.e. the GA doesn't need to include that).'''
    plan = np.array(plan)  # just in case
    if plan[0] != 'Entrance':
        plan = np.insert(plan, 0, 'Entrance')
    if plan[-1] != 'Entrance':
        plan = np.append(plan, 'Entrance')

    print("Here's the plan...")
    for i in range(plan.shape[0] - 1):
        print(i, plan[i])
        addpath(gui, locations[plan[i]], locations[plan[i + 1]])
        time.sleep(PAUSE)
    print()


def clearroute(gui):
    '''Undraw any routes on the map.'''
    for obj in gui.items[18:]:
        obj.undraw()


def makegui(imgfile):
    '''Helper function to initialize relevant graphics.'''
    # Read image from file
    img = Image(Point(0, 0), imgfile)
    hei = img.getHeight()
    wid = img.getWidth()
    img.move(wid / 2, hei / 2)

    # Create graphics window
    gui = GraphWin("Walt Disney World -- Magic Kingdom", wid, hei)
    img.draw(gui)

    return gui


def readfun(txtfile):
    '''Helper function to load the fun factor (in whatever scale you have chosen!) of each ride on the map. Output is a dictionary where keys are rides and values indicate the level of fun.'''
    with open(txtfile) as f:
        rides = list(csv.reader(f))
    fun = {ride[0]: float(ride[1]) for ride in rides}

    return fun


def readlocations(txtfile):
    '''Helper function to load the location (in pixels) of each ride on the map. Output is a dictionary where keys are rides and values are lists of [x, y] location on the image.'''
    with open(txtfile) as f:
        rides = list(csv.reader(f))
    locations = {ride[0]: [int(ride[1]), int(ride[2])] for ride in rides}

    return locations


def readridetimes(txtfile):
    '''Helper function to load the ride time (in minutes) of each ride on the map. Output is a dictionary where keys are rides and values are times.'''
    with open(txtfile) as f:
        rides = list(csv.reader(f))
    times = {ride[0]: float(ride[1]) for ride in rides}

    return times


def readwaittimes(txtfile):
    '''Helper function to load the ride time (in minutes) of each ride on the map. Output is a dictionary where keys are rides and values are times.'''
    with open(txtfile) as f:
        rides = list(csv.reader(f))
    times = {ride[0]: float(ride[1]) for ride in rides}

    return times


if __name__ == '__main__':
    main(parser.parse_args())
