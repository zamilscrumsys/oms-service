def execute(data):
    # Access members directly
    # Method 1: Using try-except
    try:
        age = data.age
    except:
        age = 0

    try:
        salary = data.salary
    except:
        salary = 0

    # Method 2: Using hasattr (might not work for foreign objects)
    # age = data.age if hasattr(data, 'age') else 0

    return {
        "valid": age >= 18 and salary >= 50000,
        "bonus": 10000 if salary > 60000 else 5000
    }