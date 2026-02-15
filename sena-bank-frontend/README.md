# Sena Bank Angular Frontend

This Angular frontend is wired to the Spring Boot backend endpoints in this repository.

## Pages
- Login page (`/`) with username/password.
- Clerk dashboard (`/clerk`) after login with `ROLE_CLERK`.
- Manager dashboard (`/manager`) after login with `ROLE_MGR`.

Every page shows **Sena Bank** in the top-left header.

## API Coverage
The UI calls all backend business endpoints:
- Auth: `/authenticate`
- Users: `/v2/users/create-clerk`
- Accounts:
  - `/v2/accounts/add`
  - `/v2/accounts/delete/{id}`
  - `/v2/accounts/accounts`
  - `/v2/accounts/accounts/{id}`
  - `/v2/accounts/{id}/with-transactions`
  - `/v2/accounts/summary`
  - `/v2/accounts/summary/by-account-number/{id}`
- Transactions:
  - `/v2/transactions/transfer`
  - `/v2/transactions/deposit`
  - `/v2/transactions/withdraw`
  - `/v2/transactions`
  - `/v2/transactions/{id}`
  - `/v2/transactions/account/{id}/count`
  - `/v2/transactions/approve-withdrawal`

## Validation/Error Behavior
Inline message **"Invalid info entered."** is shown for:
- Invalid login credentials.
- Negative or zero transaction amount.
- Missing/non-existent account numbers (when backend returns an error).


## Quick UI Preview
If package installation is blocked in your environment, you can still view a static visual preview:
```bash
cd sena-bank-frontend/preview
python3 -m http.server 4173
```
Then open `http://localhost:4173`.
Landing preview opens with only the Login page. After login, dashboard actions are visible and each button click opens a large professional popup with readable output (not JSON).
Manager preview includes **All Accounts** row-wise view with per-row **Show Transactions** button that opens an account transactions page sorted by newest timestamp first, with approval status/actions for pending withdrawals.

## Run
```bash
npm install
npm start
```

The app uses Angular proxy (`proxy.conf.json`) and calls `/api/*` so requests are forwarded to `http://localhost:8080` without browser CORS issues.
