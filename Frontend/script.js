const BASE_URL = 'http://localhost:8080'

document.addEventListener('DOMContentLoaded', () => {
  const loginSection = document.getElementById('login-section');
  const mainSection = document.getElementById('main-section');
  const loginBtn = document.getElementById('login-btn');
  const logoutBtn = document.getElementById('logout-btn');


  
  loginBtn.addEventListener('click', async () => {
      const username = document.getElementById('username').value;
      const password = document.getElementById('password').value;

      if (username && password) {
        try {
          const response = await fetch(`${BASE_URL}/login`, {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify({ username, password }),
          });

          if (response.ok) {
              const data = await response.json();
              localStorage.setItem('token', data.token); 
              loginSection.style.display = 'none';
              mainSection.style.display = 'block';
              fetchAccounts(); 
          } else {
              alert('Login failed. Please check your credentials.');
          }
      } catch (error) {
          console.error('Error during login:', error);
          alert('An error occurred. Please try again.');
      }
  }
});

  logoutBtn.addEventListener('click', () => {
      localStorage.removeItem('token');
      loginSection.style.display = 'block';
      mainSection.style.display = 'none';
  });

  // On page load, check if token exists
  if (localStorage.getItem('token')) {
      loginSection.style.display = 'none';
      mainSection.style.display = 'block';
  }
});

async function fetchAccounts() {
  try {
      const token = localStorage.getItem('token');
      const response = await fetch(`${BASE_URL}/accounts`, {
          method: 'GET',
          headers: {
              'Authorization': `Bearer ${token}`, 
          },
      });

      if (response.ok) {
          const accounts = await response.json();
          const accountList = document.getElementById('account-list');
          accountList.innerHTML = '<h3>Your Accounts:</h3>';
          accounts.forEach(account => {
              const accountDiv = document.createElement('div');
              accountDiv.textContent = `Account ID: ${account.id}, Balance: ${account.balance}`;
              accountDiv.addEventListener('click', () => {
                fetchTransactions(account.id);
                showTransferForm(account.id);
            });
              accountList.appendChild(accountDiv);
          });
      } else {
          alert('Failed to fetch accounts.');
      }
  } catch (error) {
      console.error('Error fetching accounts:', error);
  }
}

async function fetchTransactions(accountId) {
  try {
      const token = localStorage.getItem('token');
      const response = await fetch(`${BASE_URL}/accounts/${accountId}/transactions`, {
          method: 'GET',
          headers: {
              'Authorization': `Bearer ${token}`,
          },
      });

      if (response.ok) {
          const transactions = await response.json();
          const transactionsDiv = document.getElementById('transactions');
          transactionsDiv.innerHTML = '<h3>Transactions:</h3>';

          transactions.forEach(transaction => {
              const transactionDiv = document.createElement('div');
              transactionDiv.textContent = `Date: ${transaction.date}, Amount: ${transaction.amount}, Description: ${transaction.description}`;
              transactionsDiv.appendChild(transactionDiv);
          });
      } else {
          alert('Failed to fetch transactions.');
      }
  } catch (error) {
      console.error('Error fetching transactions:', error);
  }
}


function showTransferForm(fromAccountId) {
  const transferForm = document.getElementById('transfer-form');
  transferForm.style.display = 'block';
  
  const transferBtn = document.getElementById('transfer-btn');
  transferBtn.onclick = () => sendMoney(fromAccountId); // Pass fromAccountId to sendMoney function
}

async function sendMoney(fromAccountId) {
  const amount = parseFloat(document.getElementById('transfer-amount').value);
  const toAccountId = document.getElementById('to-account-id').value; 

  if (isNaN(amount) || amount <= 0) {
    alert('Please enter a valid amount.');
    return;
  }

  try {
    const token = localStorage.getItem('token');
    const response = await fetch(`${BASE_URL}/accounts/${fromAccountId}/transactions`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        amount: amount,
        fromAccount: fromAccountId,
        toAccountId: toAccountId
      }),
    });

    if (response.ok) {
      alert('Transaction successful!');
      fetchAccounts(); // Re-fetch the accounts to update balances
    } else {
      const errorMessage = await response.text();
      alert(`Transaction failed: ${errorMessage}`);
    }
  } catch (error) {
    console.error('Error during transaction:', error);
    alert('An error occurred. Please try again.');
  }
}



