import turtle

integer = java{integer}

t = turtle.Turtle()
t.speed(0)

for i in range(36):
    t.forward(100 + (integer % 50))
    t.right(170)

turtle.done()

result = f"starburst_done_{integer}"