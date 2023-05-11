import numpy as np
import matplotlib.pyplot as plt
import pdb

def mutate_uniform(population, pm: float):
    mask = np.random.random(size=population.shape) <= pm
    np.putmask(population, mask, np.random.uniform(0.0, 100.0, population.shape))
    return population

def mutate_random_reset(population, pm: float):
    mask = np.random.random(size=population.shape) <= pm
    np.putmask(population, mask, np.random.randint(0, 8, population.size))
    return population

def mutate(population, pm: float, type: str):
    mask = np.random.random(size=population.shape) <= pm
    m, n = population.shape
    if(type == "bit"):
        np.putmask(population, mask, np.abs(population - 1))
    elif(type == "randomreset"):
        return mutate_random_reset(population, pm)
    elif(type == "creep"):
        np.putmask(population, mask, population.flatten() + np.random.randint(-1, 2, population.size))
    elif(type == "uniform"):
        return mutate_uniform(population, pm)
    elif(type == "nonuniform"):
        population = np.reshape(population.flatten() + np.random.normal(0.0, pm, size=population.size), population.shape)
        population = np.minimum(np.maximum(population, 0.0), 100.0)
    elif(type == "swap"):
        for i in range(m):
            if np.random.rand() <= pm:
                indices = np.random.randint(0, n, size=(1, 2))[0]
                population[i, indices] = population[i, indices[::-1]]
    elif(type == "insert"):
        for i in range(m):
            if np.random.rand() <= pm:
                indices = sorted(np.random.randint(0, n, size=(1, 2))[0])
                x = population[i, :]
                x = np.insert(x, indices[0] + 1, x[indices[1]])
                x = np.delete(x, indices[1] + 1)
                population[i, :] = x
    elif(type == "scramble"):
        for i in range(m):
            if np.random.rand() <= 1.0:
                indices = sorted(np.random.randint(0, n, size=(1, 2))[0])
                x = population[i, :]
                np.random.shuffle(x[indices[0]:indices[1]])
                population[i, :] = x
    elif(type == "inversion"):
        for i in range(m):
            if np.random.rand() <= pm:
                indices = sorted(np.random.randint(0, n, size=(1, 2))[0])
                x = population[i, :]
                x[indices[0]:indices[1]] = x[indices[0]:indices[1]][::-1]
                population[i, :] = x
    return population

def show(population, cmap: str, fig: int, block: bool = True):
    plt.figure(fig)
    plt.imshow(population, cmap=cmap)
    plt.show(block=block)

def test():
    population = np.random.randint(0, 2, (50, 64))
    show(population, "binary", 1, False)
    # Bit-flip mutation
    population = mutate(population, 0.1, "bit")
    show(population, "binary", 2)

    population = np.random.randint(0, 8, (10, 8))
    show(population, "Blues", 3, False)
    # Random resetting mutation
    population = mutate(population, 0.2, "randomreset")
    show(population, "Blues", 4, False)
    # Creep mutation
    population = mutate(population, 0.3, "creep")
    show(population, "Blues", 5)

    population = np.random.uniform(0.0, 100.0, (5, 10))
    show(population, "viridis", 6, False)
    # Uniform mutation
    population = mutate(population, 0.1, "uniform")
    show(population, "viridis", 7, False)
    #Nonuniform mutation
    population = mutate(population, 10, "nonuniform")
    show(population, "viridis", 8)

    population = np.array([np.random.permutation(8) for _ in range(10)])
    show(population, "Blues", 8, False)
    # Swap mutation
    population = mutate(population, 0.5, "swap")
    show(population, "Blues", 9, False)
    # Insert mutation
    population = mutate(population, 1.0, "insert")
    show(population, "Blues", 10, False)
    # Scramble mutation
    population = mutate(population, 1.0, "scramble")
    show(population, "Blues", 11, False)
    # Inversion mutation
    population = mutate(population, 0.5, "inversion")
    show(population, "Blues", 12)

    # One-point crossover
    n = 10
    parents = np.random.rand(2, n) > 0.5
    children = parents.copy()
    show(parents, "binary", 1, False)
    crossoverpoint = np.random.randint(1, n - 1)
    children[:, crossoverpoint:] = parents[::-1, crossoverpoint:]
    show(children, "binary", 2)

    # Uniform crossover
    n = 10
    parents = np.random.rand(2, n) > 0.5
    children = parents.copy()
    mask = np.random.rand(n) < 0.5
    children[:, mask] = parents[::-1, mask]
    show(parents, "binary", 3, False)
    show(children, "binary", 4)

    # Simple recombination
    n = 10
    parents = np.random.uniform(0.0, 10.0, size=(2, n))
    children = parents.copy()
    alpha = 0.5
    k = np.random.randint(1, n)
    children[:, k:] = alpha * parents[::-1, k:] + (1 - alpha) * parents[:, k:]
    show(parents, "viridis", 5, False)
    show(children, "viridis", 6)

    # Single recombination
    n = 10
    parents = np.random.uniform(0.0, 10.0, size=(2, n))
    children = parents.copy()
    alpha = 0.5
    k = np.random.randint(1, n)
    children[:, k] = alpha * parents[::-1, k] + (1 - alpha) * parents[:, k]
    show(parents, "viridis", 7, False)
    show(children, "viridis", 8)

    # 1 4 3 8 2 6 5 7 9