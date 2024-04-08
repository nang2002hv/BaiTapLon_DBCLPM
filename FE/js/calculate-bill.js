// Call the dataTables jQuery plugin
$(document).ready(function () {
  $('#dataTable').DataTable();
});

var table = $('#dataTable').DataTable();
var listArea = [];
var listBill = [];
var hasWhiteDataColor = false;
// $('.confirm-and-save').prop('disabled', true);
// $('.calculate').prop('disabled', true);

setInterval(function () {
  table.rows().every(function () {
    var dataColor = $(this.node()).attr('data-color');
    if (dataColor === 'white') {
      hasWhiteDataColor = true;
      return false;
    }
  });

  if (hasWhiteDataColor) {
    $('.confirm-and-save').prop('disabled', false);
    $('.calculate').prop('disabled', false);
  }
}, 500);

document.addEventListener('DOMContentLoaded', function () {
  var employee = localStorage.getItem('employee'); // Lấy dữ liệu từ localStorage
  listArea = [];

  fetch('http://localhost:8080/api/areas/filter', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: employee
  })
    .then(response => response.json())
    .then(data => {
      listArea = data;

      var wardCommune = document.querySelector('.ward-commune');
      var district = document.querySelector('.district');
      var city = document.querySelector('.city');

      data.forEach(item => {
        if (!Array.from(wardCommune.options).some(option => option.text === item.wardCommune)) {
          var option1 = document.createElement('option');
          option1.value = item.wardCommune;
          option1.text = item.wardCommune;
          wardCommune.appendChild(option1);
        }

        if (!Array.from(district.options).some(option => option.text === item.district)) {
          var option2 = document.createElement('option');
          option2.value = item.district;
          option2.text = item.district;
          district.appendChild(option2);
        }

        if (!Array.from(city.options).some(option => option.text === item.city)) {
          var option3 = document.createElement('option');
          option3.value = item.city;
          option3.text = item.city;
          city.appendChild(option3);
        }
      });
    })
    .catch(error => console.error('Error:', error));
});

document.querySelector('.filter-by-area').addEventListener('click', function () {
  table.clear().draw();
  var wardCommune = document.querySelector('.ward-commune').value;
  var district = document.querySelector('.district').value;
  var city = document.querySelector('.city').value;

  var selectedArea = listArea.find(area => area.wardCommune === wardCommune && area.district === district && area.city === city);

  if (selectedArea) {
    listBill = [];
    

    fetch('http://localhost:8080/api/meters/filter', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(selectedArea)
    })
      .then(response => response.json())
      .then(data => {
        var promises = data.map(item => {
          return fetch('http://localhost:8080/api/bills/get-bills', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
          })
            .then(response => {
              if (response.ok) {
                return response.json();
              } else {
                throw new Error('Không tìm thấy hóa đơn nào cần được tính toán');
              }
            })
            .then(data => {
              if (data.reading.status === 'WAITING_FOR_CALCULATION') {
                listBill.push(data);
              }
              var amountBeforeTax = data.amountBeforeTax <= 0 ? '' : data.amountBeforeTax;
              var amountTax = data.amountTax <= 0 ? '' : data.amountTax;
              var amountAfterTax = data.amountAfterTax <= 0 ? '' : data.amountAfterTax;

              var rowNode = table.row.add([
                data.reading.meter.meterCode,
                data.reading.meter.customer.fullName,
                data.reading.meter.meterType,
                data.reading.previousReading,
                //'<input type="text" id="input-' + data.id + '" value="' + data.reading.currentReading + '">',
                data.reading.currentReading,
                amountBeforeTax,
                amountTax,
                amountAfterTax
            ]).draw().node();

              $(rowNode).attr('id', 'id-' + data.id);

              if (data.reading.status === 'WAITING_FOR_CALCULATION') {
                rowNode.style.backgroundColor = 'white';
                rowNode.style.color = 'black';
                $(rowNode).attr('data-color', 'white');
              }
              else if (data.reading.status === 'WAITING_FOR_PAYMENT') {
                rowNode.style.backgroundColor = 'green';
                rowNode.style.color = 'white';
                $(rowNode).attr('data-color', 'green');
              }
            });
        });

        return Promise.all(promises);
      })
      .then(() => {
        sortRow();
      })
      .catch(error => console.error('Error:', error));



  } else {
    console.log('Khu vực không tồn tại');
  }
});

function sortRow() {
  var rows = $('#dataTable tbody tr').get();

  rows.sort(function (a, b) {
    var A = $(a).attr('data-color');
    var B = $(b).attr('data-color');

    if (A > B) {
      return -1;
    }
    if (A < B) {
      return 1;
    }
    return 0;
  });

  $.each(rows, function (index, row) {
    $('#dataTable').children('tbody').append(row);
  });
}


document.querySelector('.calculate').addEventListener('click', function () {
  console.log(listBill.length)
  Promise.all(
    listBill.map((bill, index) => {
      return fetch('http://localhost:8080/api/bills/calculate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(bill)
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Bad request');
          }
        })
        .then(data => {
          var amountBeforeTax = data.amountBeforeTax <= 0 ? '' : data.amountBeforeTax;
          var amountTax = data.amountTax <= 0 ? '' : data.amountTax;
          var amountAfterTax = data.amountAfterTax <= 0 ? '' : data.amountAfterTax;
          console.log(amountBeforeTax)
          table.row('#id-' + data.id).data([
            data.reading.meter.meterCode,
            data.reading.meter.customer.fullName,
            data.reading.meter.meterType,
            data.reading.previousReading,
            data.reading.currentReading,
            amountBeforeTax,
            amountTax,
            amountAfterTax
          ]).draw();
  
          listBill[index] = data;
        })
        .catch(error => {
          console.log(error)
          // Cập nhật màu nền của hàng trong DataTables
          var rowNode = table.row('#id-' + bill.id).node();
          $(rowNode).css('background-color', 'red');
        });
    })
  ).then(() => {
    hasWhiteDataColor = false;
    sortRow();
  });
});

document.querySelector('.confirm-and-save').addEventListener('click', function () {
  var table = $('#dataTable').DataTable();
  var hasEmptyField = false;

  table.rows().every(function () {
    var data = this.data();
    data.forEach(cell => {
      if (cell === '') {
        $(this.node()).css('background-color', 'red');
        hasEmptyField = true;
      }
    });
  });

  if (!hasEmptyField) {
    listBill.forEach(bill => {
      fetch('http://localhost:8080/api/bills/save', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(bill)
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('Failed to save bill');
          }
          else {
            table.clear().draw();
            listBill = [];
            hasWhiteDataColor = false;
           
          }
        })

        .catch(error => {
          console.error('Error:', error);
        });
    });
    alert('Lưu thành công');
  }
  
});






