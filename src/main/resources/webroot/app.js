document.getElementById('fetchDataBtn').addEventListener('click', async () => {
    try {
        const response = await fetch('/data');
        const data = await response.json(); // Parsear la respuesta como JSON

        // Formatear el JSON para que sea más legible
        const formattedData = JSON.stringify(data, null, 2);

        document.getElementById('result').textContent = formattedData;
    } catch (error) {
        console.error('Error fetching data:', error);
    }
});

document.getElementById('dataForm').addEventListener('submit', async (event) => {
    event.preventDefault(); // Evitar que el formulario se envíe de manera tradicional

    const inputData = document.getElementById('dataInput').value;
    try {
        const response = await fetch('/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `name=${encodeURIComponent(inputData)}`
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const result = await response.json(); // Espera una respuesta JSON

        // Formatear el JSON para que sea más legible
        const formattedResult = JSON.stringify(result, null, 2);

        document.getElementById('postResult').textContent = formattedResult;
    } catch (error) {
        console.error('Error posting data:', error);
        document.getElementById('postResult').textContent = 'Error al enviar los datos.';
    }
});

