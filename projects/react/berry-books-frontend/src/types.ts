// 顧客統計情報の型定義
export interface CustomerStats {
  customerId: number;
  customerName: string;
  email: string;
  birthday: string;
  address: string;
  orderCount: number;
  totalBooks: number;
}

