// кнопка "найти"
function search_onclick() {
    search_address();
}

// ввод текста в поле адреса
function address_oninput() {
    const addressElement = document.getElementById('address');
     if (addressElement.value.length > 2) {
        // поиск начинается от 3 символов
        search_address();
     } else {
        const durationElement = document.getElementById('duration');
        durationElement.textContent = '-';
        const responseElement = document.getElementById('response');
        responseElement.textContent = '-';
     }
}

// поиск адреса
function search_address() {
    const apiUrl = '/api?';
    const addressElement = document.getElementById('address');
    const responseElement = document.getElementById('response');
    const durationElement = document.getElementById('duration');
    const requestOptions = { method: 'GET' };
    const requestStart = Date.now();
    // запрос к апи
    fetch(apiUrl + new URLSearchParams({ address: addressElement.value }, requestOptions))
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const responseEnd = Date.now();
            durationElement.textContent = (responseEnd - requestStart) / 1000 + ' сек.';
            return response.text();
        })
        .then(addressData => {
            const tableElement = document.createElement('table');
            tableElement.style.width = '100%';
            const tableBody = document.createElement('tbody');
            responseElement.textContent = '';
            JSON.parse(addressData)
                .forEach((addressItem) => {
                    const trElement = document.createElement('tr');
                    // схожесть
                    const simTdElement = document.createElement('td');
                    simTdElement.appendChild(
                        document.createTextNode(
                            addressItem["similarity"].slice(0, 5)
                        )
                    );
                    trElement.appendChild(simTdElement);
                    // адрес
                    const addressTdElement = document.createElement('td');
                    addressTdElement.appendChild(
                        document.createTextNode(
                            addressItem["address"]
                        )
                    );
                    trElement.appendChild(addressTdElement);
                    // идентификатор
                    const idTdElement = document.createElement('td');
                    idTdElement.appendChild(
                        document.createTextNode(
                            addressItem["id"]
                        )
                    );
                    trElement.appendChild(idTdElement);
                    tableBody.appendChild(trElement);
                });
            tableElement.appendChild(tableBody);
            responseElement.appendChild(tableElement);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
