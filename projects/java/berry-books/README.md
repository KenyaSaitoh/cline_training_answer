# berry-books プロジェクト

## 📖 概要

Jakarta EE 10とJSF (Jakarta Server Faces) 4.0を使用したオンライン書店「**Berry Books**」のWebアプリケーションです。
書籍検索、ショッピングカート、注文処理などのEC機能を実装しています。

> **Note:** このプロジェクトは**データベース初期化**を担当します。`berry-books-rest`などの関連プロジェクトと同じデータベースを共有します。

## 🚀 セットアップとコマンド実行ガイド

### 前提条件

- JDK 21以上
- Gradle 8.x以上
- Payara Server 6（プロジェクトルートの`payara6/`に配置）
- HSQLDB（プロジェクトルートの`hsqldb/`に配置）

> **Note:** ① と ② の手順は、ルートの`README.md`を参照してください。

### ③ 依存関係の確認

このプロジェクトを開始する前に、以下が起動していることを確認してください：

- **① HSQLDBサーバー** （`./gradlew startHsqldb`）
- **② Payara Server** （`./gradlew startPayara`）

### ④ プロジェクトを開始するときに1回だけ実行

```bash
# 1. データベーステーブルとデータを作成（このプロジェクトが担当）
./gradlew :projects:java:berry-books:setupHsqldb

# 2. プロジェクトをビルド
./gradlew :projects:java:berry-books:war

# 3. プロジェクトをデプロイ
./gradlew :projects:java:berry-books:deploy
```

> **重要:** `setupHsqldb`は**このプロジェクトで実行**してください。他のBerry Booksプロジェクト（`berry-books-rest`、`berry-books-frontend`）でも同じデータベースを使用します。

### ⑤ プロジェクトを終了するときに1回だけ実行（CleanUp）

```bash
# プロジェクトをアンデプロイ
./gradlew :projects:java:berry-books:undeploy
```

### ⑥ アプリケーション作成・更新のたびに実行

```bash
# アプリケーションを再ビルドして再デプロイ
./gradlew :projects:java:berry-books:war
./gradlew :projects:java:berry-books:deploy
```

## 📊 Excelファイルの展開（仕様書の確認）

このプロジェクトの`spec/`ディレクトリには、設計仕様書がExcel形式 (.xlsx) で格納されています。
Excelファイルの内部構造（XMLファイル）を確認したい場合は、以下のコマンドで展開できます。

### Excelファイルの展開方法

```bash
# specディレクトリ内のすべての.xlsxファイルを展開
./gradlew exploreExcelFiles -PtargetDir=projects/java/berry-books/spec
```

### 実行結果

実行すると、各Excelファイルと同じ階層にタイムスタンプ付きフォルダが作成され、その中にXML構造が展開されます：

```
projects/java/berry-books/spec/
├── berry-books_設計書.xlsx      # 元のExcelファイル
└── 20251029143025/              # 展開されたフォルダ（タイムスタンプ）
    ├── [Content_Types].xml
    ├── _rels/
    │   └── .rels
    ├── docProps/
    │   ├── app.xml
    │   └── core.xml
    └── xl/
        ├── workbook.xml
        ├── worksheets/
        │   ├── sheet1.xml
        │   ├── sheet2.xml
        │   └── ...
        ├── styles.xml
        ├── sharedStrings.xml
        └── _rels/
```

### 展開したファイルの活用例

- **worksheets/sheet1.xml**: 各シートのデータ構造を確認
- **sharedStrings.xml**: セル内のテキストデータを確認
- **styles.xml**: セルの書式情報を確認
- **workbook.xml**: ブック全体の構造を確認

> **Note**: Excelファイル (.xlsx) は、内部的にZIP圧縮されたXMLファイルの集合体です。このコマンドでOffice Open XML形式の内部構造を学習できます。

## 📍 アプリケーションへのログイン

デプロイ後、以下のURLにアクセス：

- **トップページ**: http://localhost:8080/berry-books

### ログイン情報

- **メールアドレス**: alice@gmail.com
- **パスワード**: password

## 🧪 テスト

### テストの実行

このプロジェクトには、サービス層のユニットテストが含まれています。テストはJUnit 5とMockitoを使用して実装されています。

#### すべてのテストを実行

```bash
./gradlew :projects:java:berry-books:test
```

#### 特定のテストクラスを実行

```bash
# OrderServiceのテストのみを実行
./gradlew :projects:java:berry-books:test --tests "*OrderServiceTest"

# BookServiceのテストのみを実行
./gradlew :projects:java:berry-books:test --tests "*BookServiceTest"
```

#### テストの継続的実行（変更検知）

```bash
./gradlew :projects:java:berry-books:test --continuous
```

### テストレポートの確認

テスト実行後、HTMLレポートが生成されます：

```
projects/java/berry-books/build/reports/tests/test/index.html
```

ブラウザで開くとテスト結果の詳細が確認できます。

### テストカバレッジの確認（JaCoCo）

```bash
# テストカバレッジレポートを生成
./gradlew :projects:java:berry-books:jacocoTestReport

# カバレッジレポートの場所
# projects/java/berry-books/build/reports/jacoco/test/html/index.html
```

### テストの構成

#### サービス層のテスト

以下のサービスクラスに対応するテストが実装されています：

| サービスクラス | テストクラス | テスト内容 |
|--------------|------------|----------|
| `OrderService` | `OrderServiceTest` | 注文処理、注文履歴取得、在庫チェック |
| `BookService` | `BookServiceTest` | 書籍検索（主キー、カテゴリ、キーワード） |
| `CategoryService` | `CategoryServiceTest` | カテゴリ一覧取得、カテゴリマップ作成 |
| `CustomerService` | `CustomerServiceTest` | 顧客登録、認証、検索 |
| `DeliveryFeeService` | `DeliveryFeeServiceTest` | 配送料金計算（通常、沖縄、送料無料） |

#### テストの特徴

- **JUnit 5** を使用（`@ExtendWith(MockitoExtension.class)`）
- **Mockito** でDAO層をモック化
- **AAA (Arrange-Act-Assert)** パターンに従った構造
- 正常系と異常系の両方をカバー
- 境界値テストとエッジケースを含む

#### テストクラスの場所

```
projects/berry-books/
└── src/
    └── test/
        └── java/
            └── dev/berry/service/
                ├── order/
                │   └── OrderServiceTest.java
                ├── book/
                │   └── BookServiceTest.java
                ├── category/
                │   └── CategoryServiceTest.java
                ├── customer/
                │   └── CustomerServiceTest.java
                └── delivery/
                    └── DeliveryFeeServiceTest.java
```

### よく使うテストコマンド

```bash
# テストをクリーンビルドして実行
./gradlew :projects:java:berry-books:clean :projects:java:berry-books:test

# テスト結果をコンソールに詳細表示
./gradlew :projects:java:berry-books:test --info

# 失敗したテストのみ再実行
./gradlew :projects:java:berry-books:test --rerun-tasks

# テストとカバレッジレポートを一括生成
./gradlew :projects:java:berry-books:test :projects:java:berry-books:jacocoTestReport
```

### CI/CD環境でのテスト実行

継続的インテグレーション環境では、以下のコマンドを使用します：

```bash
# ビルドとテストを一括実行
./gradlew :projects:java:berry-books:build

# テスト失敗時に処理を停止
./gradlew :projects:java:berry-books:test --fail-fast
```

## 🎯 プロジェクト構成

```
projects/berry-books/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── dev/berry/
│   │   │       ├── view/           # JSF Managed Bean
│   │   │       ├── service/        # ビジネスロジック
│   │   │       ├── dao/            # データアクセス層
│   │   │       ├── entity/         # JPAエンティティ
│   │   │       └── web/cart/       # セッション管理
│   │   ├── resources/
│   │   │   ├── META-INF/
│   │   │   │   └── persistence.xml  # JPA設定
│   │   │   └── static/css/
│   │   │       └── style.css        # スタイルシート
│   │   └── webapp/
│   │       ├── *.xhtml              # Facelets ビュー
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           └── beans.xml
│   └── test/
│       └── java/
│           └── dev/berry/service/   # サービス層のテスト
│               ├── order/           # 注文サービスのテスト
│               ├── book/            # 書籍サービスのテスト
│               ├── category/        # カテゴリサービスのテスト
│               ├── customer/        # 顧客サービスのテスト
│               └── delivery/        # 配送料金サービスのテスト
├── sql/
│   └── hsqldb/                      # SQLスクリプト
└── build/
    ├── libs/
    │   └── berry-books.war
    └── reports/
        ├── tests/test/              # テストレポート
        └── jacoco/test/html/        # カバレッジレポート
```

## 🔧 使用している技術

### 本番環境

- **Jakarta EE 10**
- **Payara Server 6**
- **Jakarta Faces (JSF) 4.0** - Faceletビューテンプレート
- **Jakarta Persistence (JPA) 3.1** - Hibernate実装
- **Jakarta Transactions (JTA)**
- **Jakarta CDI 4.0**
- **HSQLDB 2.7.x**

### テスト環境

- **JUnit 5** - テストフレームワーク
- **Mockito** - モックライブラリ
- **JaCoCo** - カバレッジツール（オプション）

## 🎨 デザイン仕様

- **テーマカラー**: `#CF3F4E` (ストロベリーレッド)
- **サイト名**: Berry Books
- **レスポンシブデザイン**: モダンなグラデーションとシャドウ効果

## 📦 パッケージ構成

```
dev.berry/
├── view/                # JSF Managed Bean
│   ├── BookSearchBean.java
│   ├── CartBean.java
│   └── OrderBean.java
├── service/             # ビジネスロジック（CDI Bean）
│   ├── book/
│   ├── category/
│   └── order/
├── dao/                 # データアクセス層
│   ├── BookDao.java
│   ├── CategoryDao.java
│   ├── StockDao.java
│   ├── OrderTranDao.java
│   └── OrderDetailDao.java
├── entity/              # JPAエンティティ
│   ├── Book.java
│   ├── Category.java
│   ├── Publisher.java
│   ├── Stock.java
│   ├── OrderTran.java
│   └── OrderDetail.java
└── web/cart/            # セッション管理用
    └── CartItem.java
```

## 🎯 主な機能

### 1. 書籍検索・閲覧 (`bookSearch.xhtml`)
- カテゴリやキーワードによる書籍検索
- 検索結果の一覧表示
- カートへの追加

### 2. ショッピングカート (`cart.xhtml`)
- 書籍をカートに追加
- 数量変更、削除
- セッションスコープで管理
- 合計金額の自動計算

### 3. 注文処理 (`order.xhtml`)
- 配送先入力
- 決済方法選択（クレジットカード/代金引換/銀行振込）
- 在庫チェック
- 注文確定

### 4. 注文履歴 (`orderHistory.xhtml`)
- 過去の注文履歴表示
- 注文詳細表示

## 📝 データソース設定について

このプロジェクトはルートの`build.gradle`で定義されたタスクを使用してデータソースを作成します。

### 設定内容

- **JNDI名**: `jdbc/HsqldbDS`
- **データベース**: `testdb`
- **ユーザー**: `SA`
- **パスワード**: （空文字）
- **TCPサーバー**: `localhost:9001`

データソースはPayara Serverのドメイン設定に登録されます。

### ⚠️ 注意事項

- HSQLDB Databaseサーバーが起動している必要があります
- データソース作成はPayara Server起動後に実行してください
- 初回のみ実行が必要です（2回目以降は不要）

## 🛑 アプリケーションを停止する

### アプリケーションのアンデプロイ

```bash
./gradlew :projects:java:berry-books:undeploy
```

### Payara Server全体を停止

```bash
./gradlew stopPayara
```

### HSQLDBサーバーを停止

```bash
./gradlew stopHsqldb
```

## 🔍 ログ監視

別のターミナルでログをリアルタイム監視：

```bash
tail -f -n 50 payara6/glassfish/domains/domain1/logs/server.log
```

> **Note**: Windowsでは**Git Bash**を使用してください。

## 🧪 データベースのリセット

データベースを初期状態に戻したい場合：

```bash
# HSQLDBサーバーを停止
./gradlew stopHsqldb

# データファイルを削除
rm -f hsqldb/data/testdb.*

# HSQLDBサーバーを再起動
./gradlew startHsqldb

# 初期データをセットアップ
./gradlew :projects:java:berry-books:setupHsqldb
```

## 📚 アーキテクチャ

### レイヤー構成

```
JSF View (Facelets XHTML)
    ↓
JSF Managed Bean (@Named, @ViewScoped/@SessionScoped)
    ↓
CDI Service (@ApplicationScoped)
    ↓
DAO (@ApplicationScoped)
    ↓
JPA Entity (@Entity)
    ↓
Database (HSQLDB)
```

### 主要クラス

#### 1. BookSearchBean (JSF Managed Bean)

`@Named`と`@ViewScoped`を使用。書籍検索機能を提供。

#### 2. CartBean (JSF Managed Bean)

`@Named`と`@SessionScoped`を使用。ショッピングカート管理を担当。

#### 3. OrderBean (JSF Managed Bean)

`@Named`と`@ViewScoped`を使用。注文処理と注文履歴表示を実装。

## 🔗 関連プロジェクト

- **berry-books-rest**: 同じデータベースを使用するREST APIプロジェクト（顧客管理API）
- **berry-books-frontend**: Reactで構築された管理者画面（顧客一覧・統計情報表示）

> **Note:** これらのプロジェクトは**このプロジェクト（berry-books）でセットアップしたデータベース**を使用します。

## 📖 参考リンク

- [Jakarta EE 10 Platform](https://jakarta.ee/specifications/platform/10/)
- [Jakarta Server Faces 4.0](https://jakarta.ee/specifications/faces/4.0/)
- [Facelets View Declaration Language](https://jakarta.ee/specifications/faces/4.0/jakarta-faces-4.0.html#facelets)
- [Mojarra (JSF Reference Implementation)](https://eclipse-ee4j.github.io/mojarra/)

## 📄 ライセンス

このプロジェクトは教育目的で作成されています。
