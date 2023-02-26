const UItckn = document.getElementById('tckn');
const UIname = document.getElementById('name');
const UIlastname = document.getElementById('lastname');
const UIdateofbirth = document.getElementById('dateofbirth');
const UIphonenumber = document.getElementById('phonenumber');
const UImonthlysalary = document.getElementById('monthlysalary');
const UIdeposit = document.getElementById('deposit');

inputFields = [UItckn, UIname, UIlastname, UIdateofbirth, UIphonenumber, UImonthlysalary, UIdeposit];


// Prevent spacebar usage
inputFields.forEach(input => {
  input.addEventListener('keypress', e => {
    if(e.keyCode === 32) {
      e.preventDefault();
    }
  })
})

// Show input error message
const showError = (input, message) => {

  const formControl = input.parentElement;
  formControl.className = 'form-control error';
  const small = formControl.querySelector('small');
  small.innerText = message;
}

// Show success outline
const showSuccess = input => {

  const formControl = input.parentElement;
  formControl.className = 'form-control success';
}

// Get fieldname
const getFieldName = input => {
  const label = input.parentElement.querySelector('label');
  return label.innerText;
}

// Check for empty fields
const checkRequired = inputArr => {

  inputArr.forEach(input => {

    if(input.value === "") {
      return showError(input, `${getFieldName(input)} boş bırakılamaz!`);
    }
  })
}
  
// Check input length
const checkLength = (input, min, max) => {
    
  if(input.value.length < min) {
    showError(input, `${getFieldName(input)} en az ${min} karakter olmalı!`);
  } else if(input.value.length > max) {
    showError(input, `${getFieldName(input)} en fazla ${max} karakter olmalı!`);
  } else {
    showSuccess(input);
  }
}

// Check if fields are valid
const isAllValid = inputArr => {

  const logArr = [];
  const checkArr = (success) => success === true;
 
  inputArr.forEach((input) => {
    const status = input.parentElement.classList.contains("success");
    logArr.push(status);
  });

  return logArr.every(checkArr);
};

// Save customer to backend
const saveCustomer = async () => {

  data = {
    "tckn": UItckn.value,
    "name": UIname.value,
    "lastName": UIlastname.value,
    "dateOfBirth": UIdateofbirth.value,
    "phoneNumber": UIphonenumber.value,
    "monthlySalary": UImonthlysalary.value,
    "deposit": UIdeposit.value
  }

  const response = await fetch("http://localhost:8080/add-customer", {
    method: 'POST',
    headers: {
      'Content-type': 'application/json'
    },
    body: JSON.stringify(data)
  })

  console.log("Customer created...");
}

// Apply loan and get info from backend
const applyLoan = async () => {

  await saveCustomer();
  const response = await fetch(`http://localhost:8080/apply-loan?tckn=${UItckn.value}`);
  const resData = await response.json();
  console.log(resData);
}

// Get loan from backend
const getLoan = async () => {

  await applyLoan();
  const response = await fetch(`http://localhost:8080/loans?tckn=${UItckn.value}&dateOfBirth=${UIdateofbirth.value}`);
  const resData = await response.json();
  return resData;
}

// Paint loan to frontend
const paintLoan = async () => {

  const resData = await getLoan();

  alert("Your loan amount is: " + resData[0].loanAmount + " TL");
}

// Event listeners
form.addEventListener('submit', e => {

  e.preventDefault();

  showSuccess(UIdateofbirth);
  
  checkRequired(inputFields);
  checkLength(UItckn, 11, 11);
  checkLength(UIname, 2, 25);
  checkLength(UIlastname, 2, 25);
  checkLength(UIphonenumber, 10, 10);
  checkLength(UImonthlysalary, 4, 7);
  checkLength(UIdeposit, 0, 5);
  
  
  if(isAllValid(inputFields)) {
    console.log('Validation successful...', {
      tckn: UItckn.value,
      name: UIname.value,
      lastname: UIlastname.value,
      dateofbirth: UIdateofbirth.value,
      phonenumber: UIphonenumber.value,
      monthlysalary: UImonthlysalary.value,
      deposit: UIdeposit.value,
    })

    paintLoan();

  } else {
    console.log('Validation failed...');
  }
})