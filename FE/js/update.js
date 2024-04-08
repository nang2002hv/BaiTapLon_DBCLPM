// Call the dataTables jQuery plugin
$(document).ready(function () {
  $('#dataTable').DataTable();
});

var table = $('#dataTable').DataTable();
var listArea = [];
var listMeterReading = [];
var hasWhiteDataColor = false;
var i = 0;
// $('.confirm-and-save').prop('disabled', true);
// $('.calculate').prop('disabled', true);

// setInterval(function () {
//   table.rows().every(function () {
//     var dataColor = $(this.node()).attr('data-color');
//     if (dataColor === 'white') {
//       hasWhiteDataColor = true;
//       return false;
//     }
//   });

//   if (hasWhiteDataColor) {
//     $('.confirm-and-save').prop('disabled', false);
//     $('.calculate').prop('disabled', false);
//   }
// }, 500);

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
  table.clear().draw(); // Xóa dữ liệu cũ trên bảng
  listMeter = []; // Xóa danh sách hóa đơn cũ
  i = 0;
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
          if (item.status === 'WAITING_FOR_INPUT') {
            listMeterReading.push(item);
          }
          var currentReadingValue = item.currentReading;
          var inputField = '<input type="text" id="input-' + i + '" value="' + currentReadingValue + '" disabled>';
          console.log(currentReadingValue)
          if (item.currentReading === 0) {
            // Nếu currentReading bằng 0, disable input và không set giá trị
            inputField = '<input type="text" id="input-' + i + '">';
          }
          var rowNode = table.row.add([
            item.meter.meterCode,
            item.meter.customer.fullName,
            item.meter.meterType,
            item.previousReading, // Lấy giá trị đọc trước đó hoặc hiển thị 'N/A' nếu không tồn tại
            // currentReading, // Lấy giá trị đọc hiện tại hoặc hiển thị 'N/A' nếu không tồn tại
            inputField,

          ]).draw().node();
          $(rowNode).attr('id', 'id-' + i);
          i = i + 1;
        });

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


// document.querySelector('.calculate').addEventListener('click', function () {
//   Promise.all(
//     listBill.map((bill, index) => {
//       return fetch('http://localhost:8080/api/bills/calculate', {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(bill)
//       })
//         .then(response => {
//           if (response.ok) {
//             return response.json();
//           } else {
//             throw new Error('Bad request');
//           }
//         })
//         .then(data => {
//           table.row('#id-' + data.id).data([
//             data.reading.meter.meterCode,
//             data.reading.meter.customer.fullName,
//             data.reading.meter.meterType,
//             data.reading.previousReading,
//             data.reading.currentReading,

//           ]).draw();

//           listBill[index] = data;
//         })
//         .catch(error => {
//           console.log(error)
//           // Cập nhật màu nền của hàng trong DataTables
//           var rowNode = table.row('#id-' + bill.id).node();
//           $(rowNode).css('background-color', 'red');
//         });
//     })
//   ).then(() => {
//     hasWhiteDataColor = false;
//     sortRow();
//   });
// });

document.querySelector('.confirm-and-save').addEventListener('click', function () {
  var table = $('#dataTable').DataTable();
  var hasEmptyField = false;

  table.rows().every(function () {
    var row = this;
    var rowCells = row.nodes().to$().find('input');
    rowCells.each(function () {
      var cellValue = $(this).val();
      if (cellValue === '') {
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

    listMeterReading.map((meterreading, index) => {
      // Lấy giá trị từ input dựa trên id
      var inputId = 'input-' + index; // itemId là id của input
      console.log(inputId)
      var inputValue = document.getElementById(inputId).value;

      // Gán giá trị vào biến
      meterreading.currentReading = inputValue;
      console.log(meterreading.currentReading)
      console.log(JSON.stringify(meterreading))
      return fetch('http://localhost:8080/api/metterreading/save', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(meterreading)
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Bad request');
          }
        })
        .then(data => {
          var currentReadingValue = data.currentReading;
          var inputField = '<input type="text" id="input-' + index + '" value="' + currentReadingValue + '" disabled>';

          if (data.currentReading === 0) {
            // Nếu currentReading bằng 0, disable input và không set giá trị
            inputField = '<input type="text" id="input-' + index + '">';
          }
          table.row('#id-' + index).data([
            data.meter.meterCode,
            data.meter.customer.fullName,
            data.meter.meterType,
            data.previousReading, // Lấy giá trị đọc trước đó hoặc hiển thị 'N/A' nếu không tồn tại
            // currentReading, // Lấy giá trị đọc hiện tại hoặc hiển thị 'N/A' nếu không tồn tại
            inputField,

          ]).draw();

          listMeterReading[index] = data;
          // alert("lưu thành công")
        })
        .catch(error => {
          console.log(error)
          // Cập nhật màu nền của hàng trong DataTables
          var rowNode = table.row('#id-' + index).node();
          $(rowNode).css('background-color', 'red');
        });
    })

  }
});




function isValidInput(value) {
  // Kiểm tra xem giá trị có phải là số không âm không
  if (!/^\d+(\.\d+)?$/.test(value)) {
    return false;
  }
  // Kiểm tra xem giá trị có phải là số không âm không
  if (parseFloat(value) < 0) {
    return false;
  }
  // Kiểm tra xem giá trị có chứa ký tự đặc biệt không
  if (/[^a-zA-Z0-9]/.test(value)) {
    return false;
  }
  return true;
}






