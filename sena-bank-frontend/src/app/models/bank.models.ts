export interface AuthRequest { username: string; password: string; }
export interface AuthResponse { token: string; type: string; }

export interface AccountRequest { name: string; balance: number; email: string; phone: string; }
export interface AccountResponse { id: number; name: string; balance: number; email: string; phone: string; }

export interface DepositRequest { accountId: number; amount: number; }
export interface WithdrawRequest { accountId: number; amount: number; clerkId?: string; }
export interface TransferRequest { fromAccountId: number; toAccountId: number; amount: number; clerkId?: string; }
export interface ApprovalRequest { transactionId: number; approve: boolean; }
