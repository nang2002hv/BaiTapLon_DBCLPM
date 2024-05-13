document.querySelector('#calculateForm').addEventListener('submit', function(event) {
    event.preventDefault();

    let previousReading = document.querySelector('#previous').value;
    let currentReading = document.querySelector('#current').value;

    if (previousReading === '') {
        previousReading = -1
    }
    if (currentReading === '') {
        currentReading = -1
    }


    let meterReading = {
        previousReading: previousReading,
        currentReading: currentReading
    };

    let bill = {
        reading: meterReading
    };

    console.log(bill);

    fetch('http://localhost:8080/api/bills/calculate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(bill)
    })
    .then( response => {
        if (!response.ok) {
            document.querySelector('#dataTable tbody').innerHTML = '';
            document.querySelector('#previous').value = '';
            document.querySelector('#current').value = '';
            document.querySelector('#consumed').textContent = '';
            document.querySelector('#total').textContent = '';
            document.querySelector('#totalTax').textContent = '';
            document.querySelector('#totalWithTax').textContent = '';
            throw new Error('Vui lòng nhập số điện hợp lệ!');
        }
        return response.json();
    }
    )
    .then(data => {
        console.log(data);
        document.querySelector('#previous').value = '';
        document.querySelector('#current').value = '';
        document.querySelector('#consumed').textContent = '';
        document.querySelector('#total').textContent = '';
        document.querySelector('#totalTax').textContent = '';
        document.querySelector('#totalWithTax').textContent = '';
        
        let tableBody = document.querySelector('#dataTable tbody');
        tableBody.innerHTML = '';

        for (let amountByStep of data.amountByStep) {
            let row = document.createElement('tr');

            let stepCell = document.createElement('td');
            stepCell.textContent = amountByStep.step;
            row.appendChild(stepCell);

            let priceCell = document.createElement('td');
            priceCell.textContent = amountByStep.price;
            row.appendChild(priceCell);

            let consumptionCell = document.createElement('td');
            consumptionCell.textContent = amountByStep.consumption;
            row.appendChild(consumptionCell);

            let amountCell = document.createElement('td');
            amountCell.textContent = amountByStep.amount;
            row.appendChild(amountCell);

            tableBody.appendChild(row);

        }

        document.querySelector('#consumed').textContent = data.consumption;
        document.querySelector('#total').textContent = data.amountBeforeTax;
        document.querySelector('#totalTax').textContent = data.amountTax;
        document.querySelector('#totalWithTax').textContent = data.amountAfterTax;
    })
    .catch((error) => {
        alert(error.message)
        document.querySelector('#previous').value = '';
        document.querySelector('#current').value = '';
    });
});