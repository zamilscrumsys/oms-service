function execute(data) {
    return {
        valid: data.age >= 18 && data.salary >= 50000,
        bonus: data.salary > 60000 ? 10000 : 5000
    };
}
