# jsf_person_rdb プロジェクト

## 📖 概要

JSFとJPA (Java Persistence API) を組み合わせたデータベースCRUD操作のサンプルです。
エンティティ、永続化コンテキスト、トランザクション管理を学習できます。

## 🚀 アプリケーションの開発フローとデプロイ方法

### 前提条件

- JDK 21以上
- Gradle 8.x以上
- Payara Server 6（プロジェクトルートの`payara6/`に配置）
- HSQLDB（プロジェクトルートの`hsqldb/`に配置）

### 1. HSQLDBサーバーの起動（研修1回について初回だけ）

```powershell
.\gradlew startHsqldb
```

### 2. 初期データのセットアップ（研修1回について初回だけ）

```powershell
.\gradlew :projects:jsf_person_rdb:setupHsqldb
```

### 3. Payara Serverの起動（研修1回について初回だけ）

```powershell
.\gradlew startPayara
```

### 4. データソースの作成（研修1回について初回だけ）

```powershell
.\gradlew createConnectionPool
.\gradlew createDataSource
```

### 5. WARファイルのビルド（アプリケーション作成・更新のたびに実施）

```powershell
.\gradlew :projects:jsf_person_rdb:war
```

### 6. アプリケーションのデプロイ（アプリケーション作成・更新のたびに実施）

```powershell
.\gradlew :projects:jsf_person_rdb:deploy
```

## 📍 アクセスURL

デプロイ後、以下のURLにアクセス：

- **Person一覧**: http://localhost:8080/jsf_person_rdb/faces/PersonTablePage.xhtml

## 🎯 プロジェクト構成

```
projects/jsf_person_rdb/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── bean/              # マネージドBean
│   │   │   ├── entity/            # JPAエンティティ
│   │   │   └── service/           # ビジネスロジック
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml  # JPA設定
│   │   └── webapp/
│   │       ├── *.xhtml
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           └── beans.xml
│   └── test/
├── sql/
│   └── hsqldb/                     # SQLスクリプト
└── build/
    └── libs/
        └── jsf_person_rdb.war
```

## 🔧 使用している技術

- **Jakarta EE 10**
- **Payara Server 6**
- **Jakarta Faces (JSF) 4.0**
- **Jakarta Persistence (JPA) 3.1**
- **Jakarta Transactions (JTA)**
- **Jakarta CDI 4.0**
- **HSQLDB 2.7.x**

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
- データソース作成はPayara Server起動前に実行してください
- 初回のみ実行が必要です（2回目以降は不要）

## 🛑 アプリケーションを停止する

### アプリケーションのアンデプロイ

```powershell
.\gradlew :projects:jsf_person_rdb:undeploy
```

### Payara Server全体を停止

```powershell
.\gradlew stopPayara
```

### HSQLDBサーバーを停止

```powershell
.\gradlew stopHsqldb
```

## 🔍 ログ監視

別のターミナルでログをリアルタイム監視：

**Windows (PowerShell):**
```powershell
Get-Content -Path payara6\glassfish\domains\domain1\logs\server.log -Wait -Tail 50 -Encoding UTF8
```

**Linux/Mac:**
```bash
tail -f -n 50 payara6/glassfish/domains/domain1/logs/server.log
```
