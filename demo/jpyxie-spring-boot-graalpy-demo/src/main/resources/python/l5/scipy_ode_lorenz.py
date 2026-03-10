import numpy as np
from scipy.integrate import solve_ivp

integer = java{integer}

def lorenz(t, xyz):
    x, y, z = xyz
    return [10 * (y - x), x * (28 - z) - y, x * y - 8/3 * z]

initial = [1.0, 1.0, 1.0]
t_span = [0, 2.0 + (integer % 3)]
solution = solve_ivp(lorenz, t_span, initial, max_step=0.01)

final = solution.y[:, -1]
avg = np.mean(final)

result = f"lorenz_final={final.tolist()},avg={avg:.5f}"