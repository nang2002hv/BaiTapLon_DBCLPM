
$(document).ready(function () {
  $('#dataTable').DataTable();
});

var table = $('#dataTable').DataTable();
var listArea = [];
var listMeterReadingAll =[];
var listMeterReading = [];
var hasDisabledInput = false;
var districtDataLoaded = false;

setInterval(function () {
  table.rows().every(function () {
    var inputField = $(this.node()).find('input[type="text"]')
    if (!inputField.prop('disabled')) { 
      hasDisabledInput = true; 
      return false; 
    }
  });

  if (hasDisabledInput) {
    $('.confirm-and-save').prop('disabled', false);
  } else {
    $('.confirm-and-save').prop('disabled', true);
  }
}, 500);


document.addEventListener('DOMContentLoaded', function () {
  var employee = localStorage.getItem('employee'); 
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
  district.innerHTML =""
  var option2 = document.createElement('option');
  option2.text = "Vui lòng chọn huyện";
  option2.selected = true;
  option2.disabled = true;
  district.append(option2)
  wardCommune.innerHTML =""
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
  wardCommune.innerHTML =""
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
  table.clear().draw(); // Xóa dữ liệu cũ trên bảng
  listMeter = []; // Xóa danh sách hóa đơn cũ
  // Lấy giá trị của các trường input
  var wardCommune = document.querySelector('.ward-commune').value;
  var district = document.querySelector('.district').value;
  var city = document.querySelector('.city').value;
  var selectedArea = listArea.find(area => area.wardCommune === wardCommune && area.district === district && area.city === city);
  if (selectedArea) {
    // Tạo yêu cầu POST để lấy dữ liệu từ máy chủ
    fetch('http://localhost:8080/api/meter-reading/filter', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(selectedArea)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Không thể lấy dữ liệu từ máy chủ');
        }
        return response.json();
      })
      .then(data => {
        
        data.forEach(item => {
          listMeterReadingAll.push(item)
          if (item.status === 'WAITING_FOR_INPUT') {
            listMeterReading.push(item);
          }
          var currentReadingValue = item.currentReading;
          var inputField = '<input type="text" id="input-' + item.meter.id + '" value="' + currentReadingValue + '" disabled style="background-color: green; border: none; color :white">';
        
          if (item.currentReading === 0) {
            // Nếu currentReading bằng 0, disable input và không set giá trị
            inputField = '<input type="text" id="input-' + item.meter.id + '">';
          }
          var rowNode = table.row.add([
            item.meter.meterCode,
            item.meter.customer.fullName,
            item.meter.meterType,
            item.previousReading,
             item.meter.timeUpdate,
            inputField,
            '<button id="btn-' + item.meter.id + '" class="btn btn-primary d-block mx-auto mb-4" onclick="update(\'' + item.meter.id + '\')">Cập nhật</button>'
            
          ]).draw().node();
          $(rowNode).attr('id', 'id-' + item.meter.id);
          
          if(item.status === 'WAITING_FOR_CALCULATION' || item.status === "WAITING_FOR_PAYMENT"){
            rowNode.style.backgroundColor = 'green';
            rowNode.style.color = 'white';
            $(rowNode).attr('data-color', 'green');
          } else {
            rowNode.style.backgroundColor = 'white';
            rowNode.style.color = 'black';
            $(rowNode).attr('data-color', 'white');
          }
          
          if (!$(rowNode).find('input').prop('disabled')) {
            $('#btn-' + item.meter.id).prop('disabled', true);
          }
        });
       

      })
      .then(() => {
        sortRow();
      })
      .catch(error => {
        console.error('Đã xảy ra lỗi:', error);
      });
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


function update(rowId){
  var table = $('#dataTable').DataTable();
  var rowData = table.row('#id-' + rowId).data();
  var meterCode = rowData[0];
  var inputElement = document.getElementById('input-' + rowId);
  
  inputElement.removeAttribute('disabled');
  inputElement.style.border = '1px solid black';
  inputElement.style.backgroundColor = 'white';
  inputElement.style.color = 'black';
  var itemIndex = listMeterReading.findIndex(item => item.meter.meterCode === meterCode);
  if (itemIndex === -1) {
    // Nếu không tìm thấy, thêm phần tử mới vào listMeterReading
    var newItem = listMeterReadingAll.find(item => item.meter.meterCode === meterCode);
    if (newItem) {
      listMeterReading.push(newItem);
      console.log('New item added to listMeterReading:', newItem);
    } else {
      console.log('Meter code not found in listMeterReadingAll:', meterCode);
    }
  } else {
    console.log('Item found in listMeterReading:', listMeterReading[itemIndex]);
  }
  inputElement.value = '';
  console.log(listMeterReading)
  $('#btn-' + rowId).prop('disabled', true);
  $('#id-' + rowId).removeClass('row-blue');
  $('#id-' + rowId).addClass('row-white');
  hasWhiteDataColor = false;
}




document.querySelector('.confirm-and-save').addEventListener('click', function () {
  var table = $('#dataTable').DataTable();
  var hasEmptyField = false;
  listMeterReading = listMeterReading.filter((meterreading, index) => {
    var inputId = 'input-' + meterreading.meter.id; 
    var inputElement = $('#' + inputId);
    var inputValue = inputElement.val();
    if (inputElement.prop('disabled') || !inputValue || !isValidInput(inputValue)) {
      hasEmptyField = true;
      return false; 
    }
    meterreading.currentReading = inputValue;
    return true; 
  });

  if (!hasEmptyField) {
    console.log(listMeterReading)
    Promise.all(listMeterReading.map((meterreading, index) => {
      var inputId = 'input-' + meterreading.meter.id;
      console.log(inputId)
      var inputValue = document.getElementById(inputId).value;
      console.log(inputValue)
      meterreading.currentReading = inputValue;
      return fetch('http://localhost:8080/api/meter-reading/save', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(meterreading)
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('Failed to save bill');
          }
        })
    })).then(() => {
      table.clear().draw();
      listMeterReading = [];
      listMeterReadingAll = []
      hasWhiteDataColor = false;
      alert("cập nhật số điện thành công")
    }).catch(error => {
      console.error('Error:', error);
    });
  }
});




async function isValidInput(value) {
  try {
    let response = await fetch('http://localhost:8080/api/meter-reading/validate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: value.trim()
    });
    let data = await response.text();  
    if (response.ok && data === "pass") {
      return true;
    } else {
      alert(data);  
      return false;
    }
  } catch (error) {
    console.error('Có lỗi xảy ra:', error);
    alert('Có lỗi xảy ra khi gửi yêu cầu đến server.');
    return false;
  }
}





