import turtle

integer = java{integer}

t = turtle.Turtle()
t.speed(0)

for i in range(5):
    for j in range(5):
        t.penup()
        t.goto(j * 60, i * 60)
        t.pendown()
        t.circle(20 + (integer % 30))

turtle.done()

result = f"circle_grid_{integer}"