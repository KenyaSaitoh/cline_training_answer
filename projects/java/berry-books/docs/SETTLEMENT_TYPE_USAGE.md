# SettlementType Enum 使用ガイド

## 概要

`SettlementType` は、berry-booksアプリケーションで使用される決済方法を定義するEnumクラスです。
データベースには`Integer`型で保存されますが、コード上ではこのEnumを使用することで、型安全性と可読性が向上します。

## 定義されている決済方法

| Enum値 | コード | 表示名 |
|--------|-------|---------|
| `BANK_TRANSFER` | 1 | 銀行振り込み |
| `CREDIT_CARD` | 2 | クレジットカード |
| `CASH_ON_DELIVERY` | 3 | 着払い |

## 基本的な使用方法

### 1. コードから表示名を取得

```java
// nullセーフに表示名を取得
Integer settlementCode = 1;
String displayName = SettlementType.getDisplayNameByCode(settlementCode);
// 結果: "銀行振り込み"

// nullの場合は"未選択"を返す
settlementCode = null;
displayName = SettlementType.getDisplayNameByCode(settlementCode);
// 結果: "未選択"

// 不正なコードの場合は"不明"を返す
settlementCode = 999;
displayName = SettlementType.getDisplayNameByCode(settlementCode);
// 結果: "不明"
```

### 2. コードからEnumを取得

```java
// コードからEnumに変換
Integer code = 2;
SettlementType type = SettlementType.fromCode(code);
// 結果: SettlementType.CREDIT_CARD

// Enumから情報を取得
Integer typeCode = type.getCode();           // 2
String typeName = type.getDisplayName();     // "クレジットカード"

// 不正なコードの場合は例外をスロー
try {
    type = SettlementType.fromCode(999);
} catch (IllegalArgumentException e) {
    // "Invalid settlement type code: 999"
}
```

### 3. Enumの直接使用

```java
// Enum値を直接使用
SettlementType type = SettlementType.BANK_TRANSFER;

// コードを取得
Integer code = type.getCode();  // 1

// 表示名を取得
String name = type.getDisplayName();  // "銀行振り込み"
```

### 4. 全ての決済方法コードを取得

```java
// 全てのコードを配列で取得
Integer[] allCodes = SettlementType.getAllCodes();
// 結果: [1, 2, 3]

// 全てのEnum値を取得
SettlementType[] allTypes = SettlementType.values();
// 結果: [BANK_TRANSFER, CREDIT_CARD, CASH_ON_DELIVERY]
```

## エンティティクラスでの使用

### OrderTranエンティティ

```java
OrderTran orderTran = orderTranDao.findById(orderId);

// 決済方法コードを取得
Integer settlementCode = orderTran.getSettlementType();  // 例: 2

// ヘルパーメソッドで表示名を取得
String settlementName = orderTran.getSettlementTypeName();  // "クレジットカード"
```

### CartSessionクラス

```java
CartSession cartSession = ...;

// 決済方法コードを設定
cartSession.setSettlementType(SettlementType.CREDIT_CARD.getCode());

// ヘルパーメソッドで表示名を取得
String settlementName = cartSession.getSettlementTypeName();  // "クレジットカード"
```

## JSF/Faceletsでの使用例

### XHTMLファイルで表示名を表示

```xml
<!-- 決済方法コードから表示名を表示 -->
<h:outputText value="#{orderTran.settlementTypeName}" />

<!-- または -->
<h:outputText value="#{cartSession.settlementTypeName}" />
```

## テストでの使用例

```java
@Test
void testSettlementType() {
    // Arrange
    Integer code = SettlementType.CREDIT_CARD.getCode();
    
    // Act
    String displayName = SettlementType.getDisplayNameByCode(code);
    
    // Assert
    assertEquals("クレジットカード", displayName);
    assertEquals(2, code);
}
```

## 利点

1. **型安全性**: コンパイル時に不正な値を検出できる
2. **可読性**: `1`, `2`, `3` といった数値よりも `CREDIT_CARD` の方が意味が明確
3. **保守性**: 決済方法の追加・変更が一箇所で管理できる
4. **nullセーフ**: `getDisplayNameByCode()` はnullを適切に処理
5. **バリデーション**: `fromCode()` は不正な値に対して例外をスロー

## 注意事項

- データベースには引き続き`Integer`型（1, 2, 3）で保存されます
- 既存のコードとの互換性を保つため、エンティティのフィールドは`Integer`型のままです
- 新しいコードでは積極的にこのEnumを使用してください
- XHTMLファイルの`<f:selectItem>`の`itemValue`は引き続き文字列で`"1"`, `"2"`, `"3"`を使用します

## 関連ファイル

- **Enumクラス**: `src/main/java/pro/kensait/berrybooks/common/SettlementType.java`
- **テストクラス**: `src/test/java/pro/kensait/berrybooks/common/SettlementTypeTest.java`
- **使用箇所**:
  - `OrderTran.java` - 注文エンティティ
  - `CartSession.java` - カートセッション
  - `OrderTO.java` - 注文転送オブジェクト
  - `bookOrder.xhtml` - 注文入力画面

