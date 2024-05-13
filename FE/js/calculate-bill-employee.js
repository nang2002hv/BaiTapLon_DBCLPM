// Call the dataTables jQuery plugin
$(document).ready(function () {
  $('#dataTable').DataTable();
});

var table = $('#dataTable').DataTable();
var listArea = [];
var listBill = [];
var hasWhiteDataColor = false;
var districtDataLoaded = false;
$('.confirm-and-save').prop('disabled', true);
$('.calculate').prop('disabled', true);

setInterval(function () {
  console.log(hasWhiteDataColor)
  if (hasWhiteDataColor) {
    $('.confirm-and-save').prop('disabled', false);
    $('.calculate').prop('disabled', false);
  }
  else {
    $('.confirm-and-save').prop('disabled', true);
    $('.calculate').prop('disabled', true);
  }
}, 500);

document.addEventListener('DOMContentLoaded', function () {
  var employee = localStorage.getItem('employee'); // Lấy dữ liệu từ localStorage
  console.log(employee)
  listMeter = [];
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
      var addedPlaceholderWardCommune = false;
      var addedPlaceholderDistrict = false;
      var addedPlaceholderCity = false;

      data.forEach(item => {
        if (!addedPlaceholderWardCommune && !Array.from(wardCommune.options).some(option => option.text === item.wardCommune)) {
          var option1 = document.createElement('option');
          option1.text = "Vui lòng chọn xã";
          option1.disabled = true;
          option1.selected = true;
          wardCommune.appendChild(option1);
          addedPlaceholderWardCommune = true;

        }

        if (!addedPlaceholderDistrict && !Array.from(district.options).some(option => option.text === item.district)) {
          var option2 = document.createElement('option');
          option2.text = "Vui lòng chọn huyện";
          option2.selected = true;
          option2.disabled = true;
          district.appendChild(option2);
          addedPlaceholderDistrict = true;
        }

        if (!addedPlaceholderCity && !Array.from(city.options).some(option => option.text === item.city)) {
          var option3 = document.createElement('option');
          option3.text = "Vui lòng chọn thành phố";
          city.appendChild(option3);
          addedPlaceholderCity = true;
        }
      });
    })
    .catch(error => console.error('Error:', error));
});

document.querySelector('.city').addEventListener('click', function () {
  var nameCity = document.querySelector(".city");
  nameCity.innerHTML = "";
  var addedCities = [];
  listArea.forEach(function (item) {
    if (!addedCities.includes(item.city)) {
      var option = document.createElement("option");
      option.text = item.city;
      option.value = item.city;
      nameCity.add(option);
      addedCities.push(item.city);
    }
  });
  var wardCommune = document.querySelector('.ward-commune');
  var district = document.querySelector('.district');
  district.innerHTML = ""
  var option2 = document.createElement('option');
  option2.text = "Vui lòng chọn huyện";
  option2.selected = true;
  option2.disabled = true;
  district.append(option2)
  wardCommune.innerHTML = ""
  var option1 = document.createElement('option');
  option1.text = "Vui lòng chọn xã";
  option1.disabled = true;
  option1.selected = true;
  wardCommune.appendChild(option1);
  districtDataLoaded = false;
});

document.querySelector('.district').addEventListener('click', function () {
  var nameCitys = document.querySelector(".city").value;
  console.log(nameCitys)
  var districtList = getListDistrictByNameCity(nameCitys);
  var districtSelect = document.querySelector('.district');

  // Kiểm tra xem dữ liệu đã được tải hay chưa
  if (!districtDataLoaded && nameCitys !== "Vui lòng chọn thành phố") {
    districtSelect.disabled = false;
    districtSelect.innerHTML = "";
    districtList.forEach(function (district) {
      var option = document.createElement("option");
      option.text = district;
      option.value = district;
      districtSelect.add(option);
    });

    // Đánh dấu rằng dữ liệu đã được tải
    districtDataLoaded = true;
  }
  var wardCommune = document.querySelector('.ward-commune');
  wardCommune.innerHTML = ""
  var option1 = document.createElement('option');
  option1.text = "Vui lòng chọn xã";
  option1.disabled = true;
  option1.selected = true;
  wardCommune.appendChild(option1);
});

document.querySelector('.ward-commune').addEventListener('click', function () {

  var nameDistricts = document.querySelector(".district").value;
  var wardCommunetList = getListwardCommuneByNameDistrict(nameDistricts);
  var wardCommunetSelect = document.querySelector('.ward-commune');

  if (nameDistricts != "Vui lòng chọn huyện") {
    wardCommunetSelect.disabled = false;
    wardCommunetSelect.innerHTML = "";
    wardCommunetList.forEach(function (wardCommune) {
      var option = document.createElement("option");
      option.text = wardCommune;
      option.value = wardCommune;
      wardCommunetSelect.add(option);
    });
  }
});



function getListDistrictByNameCity(nameCity) {
  var listDistrict = [];
  for (var i = 0; i < listArea.length; i++) {
    if (listArea[i].city === nameCity) {
      listDistrict.push(listArea[i].district);
    }
  }
  return listDistrict;
}

function getListwardCommuneByNameDistrict(nameDistrict) {
  var listWardCommuneList = [];
  for (var i = 0; i < listArea.length; i++) {
    if (listArea[i].district === nameDistrict) {
      listWardCommuneList.push(listArea[i].wardCommune);
    }
  }
  return listWardCommuneList;
}

document.querySelector('.filter-by-area').addEventListener('click', function () {
  table.clear().draw();
  listBill = [];
  var wardCommune = document.querySelector('.ward-commune').value;
  var district = document.querySelector('.district').value;
  var city = document.querySelector('.city').value;

  var selectedArea = listArea.find(area => area.wardCommune === wardCommune && area.district === district && area.city === city);

  if (selectedArea) {

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
                hasWhiteDataColor = true;
              }
              var amountBeforeTax = data.reading.status === 'WAITING_FOR_CALCULATION' ? '' : data.amountBeforeTax;
              var amountTax = data.reading.status === 'WAITING_FOR_CALCULATION' ? '' : data.amountTax;
              var amountAfterTax = data.reading.status === 'WAITING_FOR_CALCULATION' ? '' : data.amountAfterTax;
              var status = data.reading.status === 'WAITING_FOR_CALCULATION' ? 'Chờ tính toán' : 'Đã lưu';
              var dateUpdate = data.reading.status === 'WAITING_FOR_CALCULATION' ? '' : data.dateUpdate;

              var rowNode = table.row.add([
                data.reading.meter.meterCode,
                data.reading.meter.customer.fullName,
                data.reading.meter.meterType,
                data.reading.previousReading,
                data.reading.currentReading,
                amountBeforeTax,
                amountTax,
                amountAfterTax,
                dateUpdate,
                status
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

          table.row('#id-' + data.id).data([
            data.reading.meter.meterCode,
            data.reading.meter.customer.fullName,
            data.reading.meter.meterType,
            data.reading.previousReading,
            data.reading.currentReading,
            amountBeforeTax,
            amountTax,
            amountAfterTax,
            "",
            "Đã tính toán"
          ]).draw();
          $('#id-' + data.id).css('background-color', 'white');
          $('#id-' + data.id).attr('data-color', 'white');
          listBill[index] = data;
          // console.log(listBill[index])
        })
        .catch(error => {
          console.log(error)
          // Cập nhật màu nền của hàng trong DataTables
          var rowNode = table.row('#id-' + bill.id).node();
          $(rowNode).css('background-color', 'red');
        });
    })
  ).then(() => {
    // hasWhiteDataColor = false;
    sortRow();
  });
});

document.querySelector('.confirm-and-save').addEventListener('click', function () {
  var table = $('#dataTable').DataTable();
  var hasEmptyField = false;

  table.rows().every(function () {
    var data = this.data();
    data.forEach(cell => {
      var emptyField = 0
      if (cell === '' ) {
        emptyField++;
        
        if(emptyField > 1)
          hasEmptyField = true;
      }
      if(emptyField > 1) {
        $(this.node()).css('background-color', 'red');
        $(this.node()).attr('data-color', 'red');
      }
    });
  });

  if (!hasEmptyField) {
    Promise.all(
      listBill.map(bill => {
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
              return response.json();
            }
          })
          .then(data => {
            var rowNode = table.row('#id-' + bill.id).node();
            rowNode.style.backgroundColor = 'green';
            rowNode.style.color = 'white';
            $(rowNode).attr('data-color', 'green');

            var amountBeforeTax = data.reading.status === 'WAITING_FOR_CALCULATION' ? '' : data.amountBeforeTax;
            var amountTax = data.reading.status === 'WAITING_FOR_CALCULATION' ? '' : data.amountTax;
            var amountAfterTax = data.reading.status === 'WAITING_FOR_CALCULATION' ? '' : data.amountAfterTax;
            var status = data.reading.status === 'WAITING_FOR_CALCULATION' ? 'Đã tính toán' : 'Đã lưu';
            var dateUpdate = data.reading.status === 'WAITING_FOR_CALCULATION' ? '' : data.dateUpdate;
            
            table.row('#id-' + data.id).data([
              data.reading.meter.meterCode,
              data.reading.meter.customer.fullName,
              data.reading.meter.meterType,
              data.reading.previousReading,
              data.reading.currentReading,
              amountBeforeTax,
              amountTax,
              amountAfterTax,
              dateUpdate,
              status
            ]).draw();
          })
          .catch(error => {
            console.error('Error:', error);
            alert('Lưu thất bại');
          });
      })
    ).then(() => {
      hasWhiteDataColor = false;
      listBill = [];
      alert('Lưu thành công');
    });
  }
  else {
    alert('Vui lòng tính toán hóa đơn trước khi lưu')
  }

});






