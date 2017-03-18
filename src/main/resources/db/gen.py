n = 15

def boolean(x):
    return "TRUE" if x else "FALSE"

for i in range(n):
    sql = "INSERT INTO BOXES (ID, INDOOR, WINDOWS, DAILY_RATE, AREA, NAME) VALUES ({}, {}, {}, {}, {}, '{}');".format(i, boolean(i%2), boolean(i%2), i, i, "box_{}".format(i))
    print(sql)
