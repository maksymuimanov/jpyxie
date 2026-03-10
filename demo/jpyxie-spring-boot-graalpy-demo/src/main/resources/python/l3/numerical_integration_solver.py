import math

integer = int(java{integer})

def integrate(f, a, b, steps):
    step = (b - a) / steps
    total = 0.0

    x = a
    for _ in range(steps):
        total += f(x) * step
        x += step

    return total

def func(x):
    return math.sin(x) + math.cos(x * 2)

steps = 200000 + integer * 1000

value = integrate(func, 0, math.pi, steps)

result = f"integral={value}"