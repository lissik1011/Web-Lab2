import {draw} from "./canvas.js";

const button = document.querySelector('.query');
export const canvas = document.getElementById('canvas');
export const ctx = canvas.getContext("2d");

export function Y() {
    return document.querySelector('input[name="Y"]:checked');
}

const checkboxes = document.querySelectorAll('input[type="checkbox"][name="R"]');
const radios = document.querySelectorAll('input[type="radio"][name="Y"]');

document.querySelector('input[type="radio"][name="Y"][value="0"]').checked = true;

let hv = document.getElementById('hiddenR').value;
checkboxes.forEach(cb => {
  cb.checked = false;
  if (cb.value === hv) cb.checked = true;
});

checkboxes.forEach(box => {
  box.addEventListener('click', function() {
    checkboxes.forEach(cb => {cb.checked = false;});
    this.checked = true;
    let checkedvalue = this.getAttribute('value');
    document.getElementById('hiddenR').value = checkedvalue;
  });
});

fetch('controller', {
    method: 'GET'
}).then(response => {
    if (!response.ok) {
    return response.text().then(text => {
        throw new Error(`HTTP ${response.status}: ${text}`);
      })
    }
    return response.text();
})
.then(htmlRow => {
    const tbody = document.querySelector('#table tbody');
    tbody.innerHTML = htmlRow;
    return htmlRow;
})
.catch(error => {
    console.error('Fetch failed: ', {
    message: error.message,
    stack: error.stack,
    name: error.name
  });
});

button.addEventListener('click', () => {
    doFetch();
});

function doFetch() {
  let body = makeForm();
    if (body == null) {
        return;
    }
  fetch('controller', {
        method: 'POST',
        body: body
    }).then(response => {
        if (!response.ok) {
        return response.text().then(text => {
            throw new Error(`HTTP ${response.status}: ${text}`);
          })
        }
        return response.text();
    })
    .then(htmlRow => {
        const tbody = document.querySelector('#table tbody');
        tbody.innerHTML = htmlRow;
        return htmlRow;
    })
    .catch(error => {
        console.error('Fetch failed: ', {
        message: error.message,
        stack: error.stack,
        name: error.name
    });
    console.log('Ошибка при отправке запроса:\n' + error.message);
    })
}

export const R = document.getElementById("hiddenR");
export const X = document.getElementById("X");

X.addEventListener('paste', (e) => {
    e.preventDefault(); 
});
X.addEventListener("input", validateText);


function validateText(e) {
    console.log("Start of validating X");

    e.target.value = e.target.value.replace(/[^0-9.-]/g, "");
    const input = e.target;
    const selectionStart = input.selectionStart;
    let value = input.value;

    if (value === "" || value === "-" || value === ".") {
        return;
    }

    if (isNaN(Number(value))) {
        input.value = value.slice(0, selectionStart - 1) + value.slice(selectionStart);
        input.setSelectionRange(selectionStart - 1, selectionStart - 1);
    }
    if (Number(value) > 3) {
        input.value = value.slice(0, selectionStart - 1) + value.slice(selectionStart);
        input.setSelectionRange(selectionStart - 1, selectionStart - 1);
    }
    if (Number(value) < -5) {
        input.value = value.slice(0, selectionStart - 1) + value.slice(selectionStart);
        input.setSelectionRange(selectionStart - 1, selectionStart - 1);
    }
}

function makeForm() {
    if (!checkForm()) {
        return null;
    }
    
    const formData = new URLSearchParams();
    formData.append('X', X.value);
    formData.append('Y', Y().value);
    formData.append('R', R.value);

    return formData;
}

function checkForm() {

    let r = R.value;
    let x = X.value;
    let y = Y().value;
    console.log("R:", r, "X:", x, "Y:", y);

    if (!r) {
        alert("Радиус R не выбран!");
        return false;
    }
    if (!y) {
        alert("Y не выбран!");
        return false;
    }
    if (!x) {
        alert("Поле X не заполнено!");
        return false;
    }
    if (x < -5 || x > 3) {
        alert("Значение X должно быть от -5 до 3");
        return false;
    }

    console.log("Form is complited.");
    return true;
}


draw();

X.addEventListener("input", draw);

radios.forEach(r => {
  r.addEventListener('click', draw);
});

checkboxes.forEach(cb => {
  cb.addEventListener('click', draw);
});

canvas.addEventListener('click', (e) => {
    const rect = canvas.getBoundingClientRect();

    let xNum = ((e.clientX - rect.left - 190)/30);
    let yNum = (-(e.clientY - rect.top - 190)/30);
    xNum = Math.max(-5, Math.min(3, xNum));
    yNum = Math.max(-5, Math.min(3, yNum));
    const x = xNum.toFixed(5).toString();
    const y = Math.round(yNum).toString();

    document.getElementById("X").value = x;
    radios.forEach(r => {
      if (r.getAttribute('value') === y) r.checked = true;
    });

    draw();
    doFetch();
});



