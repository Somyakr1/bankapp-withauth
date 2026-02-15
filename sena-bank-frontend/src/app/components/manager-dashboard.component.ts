import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-manager-dashboard',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="page">
      <h2>Manager Dashboard</h2>
      <div class="grid two">
        <div class="card">
          <h3>Create Clerk</h3>
          <form [formGroup]="clerkForm" (ngSubmit)="createClerk()">
            <label>Username</label><input formControlName="username" />
            <label>Password</label><input type="password" formControlName="password" />
            <button type="submit">Create Clerk</button>
          </form>
        </div>

        <div class="card">
          <h3>Manage Accounts</h3>
          <form [formGroup]="addAccountForm" (ngSubmit)="addAccount()">
            <label>Name</label><input formControlName="name" />
            <label>Balance</label><input type="number" formControlName="balance" />
            <label>Email</label><input formControlName="email" />
            <label>Phone</label><input formControlName="phone" />
            <button type="submit">Add Account</button>
          </form>

          <label>Delete Account Number</label>
          <input type="number" [formControl]="accountIdControl" />
          <button class="danger" (click)="deleteAccount()">Delete Account</button>
          <button (click)="getAllAccounts()">All Accounts</button>
          <button (click)="getAccountById()">By Account</button>
          <button (click)="getAccountWithTx()">With Transactions</button>
          <button (click)="getSummary()">Summary</button>
          <button (click)="getSummaryByAccount()">Summary by Account</button>
        </div>

        <div class="card">
          <h3>Transactions (Clerk + Manager)</h3>
          <form [formGroup]="depositForm" (ngSubmit)="deposit()">
            <label>Deposit Account</label><input type="number" formControlName="accountId" />
            <label>Amount</label><input type="number" formControlName="amount" />
            <button type="submit">Deposit</button>
          </form>
          <form [formGroup]="withdrawForm" (ngSubmit)="withdraw()">
            <label>Withdraw Account</label><input type="number" formControlName="accountId" />
            <label>Amount</label><input type="number" formControlName="amount" />
            <button type="submit">Withdraw</button>
          </form>
          <form [formGroup]="transferForm" (ngSubmit)="transfer()">
            <label>From Account</label><input type="number" formControlName="fromAccountId" />
            <label>To Account</label><input type="number" formControlName="toAccountId" />
            <label>Amount</label><input type="number" formControlName="amount" />
            <button type="submit">Transfer</button>
          </form>
        </div>

        <div class="card">
          <h3>Manager-only Transaction Controls</h3>
          <label>Transaction ID</label><input type="number" [formControl]="txIdControl" />
          <label>Account Number for Tx Count</label><input type="number" [formControl]="txCountAccountId" />
          <div>
            <button (click)="getAllTx()">All Transactions</button>
            <button (click)="getTxById()">Transaction By ID</button>
            <button (click)="getTxCount()">Transaction Count</button>
          </div>

          <form [formGroup]="approvalForm" (ngSubmit)="approveWithdrawal()">
            <label>Approve pending withdrawal?</label>
            <select formControlName="approve"><option [ngValue]="true">Approve</option><option [ngValue]="false">Reject</option></select>
            <button type="submit">Submit Approval</button>
          </form>
        </div>
      </div>

      <div class="inline-error" *ngIf="errorMessage">{{ errorMessage }}</div>
      <pre class="result">{{ resultText }}</pre>
    </div>
  `
})
export class ManagerDashboardComponent {
  errorMessage = '';
  resultText = 'Awaiting action...';

  accountIdControl = this.fb.control<number | null>(null);
  txIdControl = this.fb.control<number | null>(null);
  txCountAccountId = this.fb.control<number | null>(null);

  clerkForm = this.fb.group({ username: ['', Validators.required], password: ['', Validators.required] });
  addAccountForm = this.fb.group({ name: ['', Validators.required], balance: [0, Validators.required], email: ['', Validators.required], phone: ['', Validators.required] });
  depositForm = this.fb.group({ accountId: [0, Validators.required], amount: [0, Validators.required] });
  withdrawForm = this.fb.group({ accountId: [0, Validators.required], amount: [0, Validators.required] });
  transferForm = this.fb.group({ fromAccountId: [0, Validators.required], toAccountId: [0, Validators.required], amount: [0, Validators.required] });
  approvalForm = this.fb.group({ approve: [true, Validators.required] });

  constructor(private fb: FormBuilder, private api: ApiService) {}

  createClerk(): void {
    if (this.clerkForm.invalid) return this.invalidInfo();
    this.execute(() => this.api.createClerk(this.clerkForm.getRawValue() as { username: string; password: string }));
  }

  addAccount(): void {
    const value = this.addAccountForm.getRawValue();
    if (!value.name || value.balance < 0 || !value.email || !value.phone) return this.invalidInfo();
    this.execute(() => this.api.addAccount(value as { name: string; balance: number; email: string; phone: string }));
  }

  deleteAccount(): void { this.executeWithControl(this.accountIdControl, (id) => this.api.deleteAccount(id)); }
  getAllAccounts(): void { this.execute(() => this.api.getAllAccounts()); }
  getAccountById(): void { this.executeWithControl(this.accountIdControl, (id) => this.api.getAccountById(id)); }
  getAccountWithTx(): void { this.executeWithControl(this.accountIdControl, (id) => this.api.getAccountWithTransactions(id)); }
  getSummary(): void { this.execute(() => this.api.getAccountSummary()); }
  getSummaryByAccount(): void { this.executeWithControl(this.accountIdControl, (id) => this.api.getAccountSummaryByAccount(id)); }

  deposit(): void {
    const value = this.depositForm.getRawValue();
    if (value.amount <= 0 || value.accountId <= 0) return this.invalidInfo();
    this.execute(() => this.api.deposit({ accountId: value.accountId, amount: value.amount }));
  }

  withdraw(): void {
    const value = this.withdrawForm.getRawValue();
    if (value.amount <= 0 || value.accountId <= 0) return this.invalidInfo();
    this.execute(() => this.api.withdraw({ accountId: value.accountId, amount: value.amount }));
  }

  transfer(): void {
    const value = this.transferForm.getRawValue();
    if (value.amount <= 0 || value.fromAccountId <= 0 || value.toAccountId <= 0) return this.invalidInfo();
    this.execute(() => this.api.transfer({ fromAccountId: value.fromAccountId, toAccountId: value.toAccountId, amount: value.amount }));
  }

  getAllTx(): void { this.execute(() => this.api.getAllTransactions()); }
  getTxById(): void { this.executeWithControl(this.txIdControl, (id) => this.api.getTransactionById(id)); }
  getTxCount(): void { this.executeWithControl(this.txCountAccountId, (id) => this.api.getTransactionCount(id)); }

  approveWithdrawal(): void {
    const txId = this.txIdControl.value ?? 0;
    const approve = this.approvalForm.getRawValue().approve ?? true;
    if (txId <= 0) return this.invalidInfo();
    this.execute(() => this.api.approveWithdrawal({ transactionId: txId, approve }));
  }

  private executeWithControl(control: { value: number | null }, request: (id: number) => any): void {
    const id = control.value ?? 0;
    if (id <= 0) return this.invalidInfo();
    this.execute(() => request(id));
  }

  private execute(request: () => any): void {
    this.errorMessage = '';
    request().subscribe({
      next: (res: unknown) => (this.resultText = JSON.stringify(res, null, 2)),
      error: () => this.invalidInfo()
    });
  }

  private invalidInfo(): void {
    this.errorMessage = 'Invalid info entered.';
  }
}
