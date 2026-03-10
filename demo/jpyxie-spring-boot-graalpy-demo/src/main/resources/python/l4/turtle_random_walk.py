import turtle
import random

integer = java{integer}

t = turtle.Turtle()
t.speed(0)

for _ in range(200 + (integer % 100)):
    t.forward(random.randint(5,15))
    t.right(random.randint(0, 360))

turtle.done()

result = f"random_walk_{integer}"