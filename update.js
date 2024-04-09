// Call the dataTables jQuery plugin
$(document).ready(function () {
  $('#dataTable').DataTable();
});

var table = $('#dataTable').DataTable();
var listArea = [];
var listMeterReadingAll =[];
var listMeterReading = [];
var hasWhiteDataColor = false;
var districtDataLoaded = false;

$('.confirm-and-save').prop('disabled', true);

setInterval(function () {
  table.rows().every(function () {
    var inputField = $(this.node()).find('input[type="text"]')
    if (!inputField.prop('disabled')) { // Kiểm tra xem input có disabled không
      hasDisabledInput = true; // Nếu có input bị tắt, gán biến hasDisabledInput thành true
      return false; // Dừng lặp
    }
  });

  if (hasDisabledInput) {
    $('.confirm-and-save').prop('disabled', false);
  } else {
    $('.confirm-and-save').prop('disabled', true);
  }
}, 500);

document.addEventListener('DOMContentLoaded', function () {
  var employee = localStorage.getItem('employee'); // Lấy dữ liệu từ localStorage
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
    fetch('http://localhost:8080/api/metterreading/filter', {
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
          var inputField = '<input type="text" id="input-' + item.meter.id + '" value="' + currentReadingValue + '" disabled>';
        
          if (item.currentReading === 0) {
            // Nếu currentReading bằng 0, disable input và không set giá trị
            inputField = '<input type="text" id="input-' + item.meter.id + '">';
          }
          var rowNode = table.row.add([
            item.meter.meterCode,
            item.meter.customer.fullName,
            item.meter.meterType,
            item.meter.timeUpdate,
            item.previousReading,
            inputField,
            '<button id="btn-' + item.meter.id + '" class="btn btn-primary d-block mx-auto mb-4" onclick="update(\'' + item.meter.id + '\')">Cập nhật</button>'
            
          ]).draw().node();
          $(rowNode).attr('id', 'id-' + item.meter.id);
          
          if(item.status === 'WAITING_FOR_CALCULATION' || item.status === "WAITING_FOR_PAYMENT"){
            $(rowNode).addClass('row-blue');
          }
          if (!$(rowNode).find('input').prop('disabled')) {
            $('#btn-' + item.meter.id).prop('disabled', true);
          }
        });
       

      })
      
      .catch(error => {
        console.error('Đã xảy ra lỗi:', error);
      });
  }
});

function update(rowId){
  var table = $('#dataTable').DataTable();
  var rowData = table.row('#id-' + rowId).data();
  var meterCode = rowData[0];
  var inputElement = document.getElementById('input-' + rowId);
  
  inputElement.removeAttribute('disabled');
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
  $('.confirm-and-save').prop('disabled', false);
}



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



document.querySelector('.confirm-and-save').addEventListener('click', function () {
  var table = $('#dataTable').DataTable();
  var hasEmptyField = false;

  table.rows().every(function () {
    var row = this;
    var rowCells = row.nodes().to$().find('input');
    rowCells.each(function () {
      var cellValue = $(this).val();
      if (cellValue === '') {
        alert("Không được bỏ trống ô nhập số điện")
        $(row.node()).css('background-color', 'red');
        hasEmptyField = true;
      } else if (!isValidInput(cellValue)) {
        $(row.node()).css('background-color', 'red');
        $(this).val('');
        hasEmptyField = true;
      }
    });
  });

  if (!hasEmptyField) {
    console.log(listMeterReading)
    listMeterReading.map((meterreading, index) => {
      
      // Lấy giá trị từ input dựa trên id
      var inputId = 'input-' + meterreading.meter.id; // itemId là id của input
      console.log(inputId)
      var inputValue = document.getElementById(inputId).value;
      console.log(inputValue)
      // Gán giá trị vào biến
      meterreading.currentReading = inputValue;
      return fetch('http://localhost:8080/api/metterreading/save', {
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
          else {
            alert("cập nhật số điện thành công")
            table.clear().draw();
            listMeterReading = [];
            listMeterReadingAll = []
            hasWhiteDataColor = false;
           
          }
        })
        // .then(data => {
        //   var currentReadingValue = data.currentReading;
        //   var inputField = '<input type="text" id="input-' + meterreading.meter.id + '" value="' + currentReadingValue + '" disabled>';

        //   if (data.currentReading === 0) {
        //     // Nếu currentReading bằng 0, disable input và không set giá trị
        //     inputField = '<input type="text" id="input-' + meterreading.meter.id + '">';
        //   }
        //   table.row('#id-' + meterreading.meter.id).data([
        //     data.meter.meterCode,
        //     data.meter.customer.fullName,
        //     data.meter.meterType,
        //     item.meter.timeUpdate,
        //     data.previousReading, 
        //     inputField,
        //     '<button id="btn-' + meterreading.meter.id + '" class="btn btn-primary d-block mx-auto mb-4" onclick="update(\'' + meterreading.meter.id + '\')">Cập nhật</button>'
        //   ]).draw();
        //   if(item.status === 'WAITING_FOR_CALCULATION' || item.status === "WAITING_FOR_PAYMENT"){
        //     $(rowNode).removeClass('row-white')
        //     $(rowNode).addClass('row-blue');
        //   }

        //   listMeterReading[index] = data;
        //   // alert("lưu thành công")
        // })
        // .catch(error => {
        //   console.log(error)
        //   // Cập nhật màu nền của hàng trong DataTables
        //   var rowNode = table.row('#id-' + meterreading.meter.id).node();
        //   $(rowNode).css('background-color', 'green');
        //   $(rowNode).css('color', 'white');
        // });
    })

  }
});




function isValidInput(value) {
  // Kiểm tra xem giá trị có phải là số không âm không
  if (!/^\d+(\.\d+)?$/.test(value)) {
    alert("Không được nhập kí tự đặc biết")
    return false;
  }
  
  // Kiểm tra xem giá trị có phải là số không âm không
  if (parseFloat(value) < 0) {
    alert("Không được nhập số âm")
    return false;
  }
  if(value.length > 17){
    alert("Không được nhập số lớn hơn 17 chữ số")
    return false;
  }
  // Kiểm tra xem giá trị có chứa ký tự đặc biệt không
  if (/[^a-zA-Z0-9]/.test(value)) {
    alert("Không nhập chữ cái")
    return false;
  }
  return true;
}






