 const timer = document.getElementById("timer");
    const startButton = document.getElementById("startButton");
    const stopButton = document.getElementById("stopButton");

    let startTime = 0;
    let interval;

    function updateTimer() {
    const currentTime = new Date().getTime();
    const elapsedTime = new Date(currentTime - startTime);
    timer.textContent = elapsedTime.toISOString().substr(11, 8);
}

    startButton.addEventListener("click", () => {
    if (!interval) {
    startTime = new Date().getTime();
    interval = setInterval(updateTimer, 1000);
}
});

    stopButton.addEventListener("click", () => {
    clearInterval(interval);
    interval = null;
});
