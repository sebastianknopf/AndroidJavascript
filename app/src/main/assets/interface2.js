(function () {
    let number = api.getRandomNumber();
    api.verbose('Random Value: ' + number);

    let result = number * 2;

    api.setCalculationResult(result);

    return true;
})()