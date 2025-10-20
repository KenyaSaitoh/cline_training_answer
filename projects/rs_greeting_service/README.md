# rs_greeting_service プロジェクト

## 📖 概要

JAX-RSのより高度な機能を学ぶサンプルプロジェクトです。
JSON処理、クエリパラメータ、HTTPステータスコードの制御を学習できます。

## 🚀 アプリケーションの開発フローとデプロイ方法

### 前提条件

- JDK 21以上
- Gradle 8.x以上
- Payara Server 6（プロジェクトルートの`payara6/`に配置）

### 1. Payara Serverの起動（研修1回について初回だけ）

```powershell
.\gradlew startPayara
```

### 2. WARファイルのビルド（アプリケーション作成・更新のたびに実施）

```powershell
.\gradlew :projects:rs_greeting_service:war
```

### 3. アプリケーションのデプロイ（アプリケーション作成・更新のたびに実施）

```powershell
.\gradlew :projects:rs_greeting_service:deploy
```

## 📍 アクセスURL

デプロイ後、以下のURLにアクセス：

- **Greeting API**: http://localhost:8080/rs_greeting_service/api/greeting

## 🎯 プロジェクト構成

```
projects/rs_greeting_service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── application/       # JAX-RS Application
│   │   │   ├── resource/          # REST Resource
│   │   │   └── model/             # データモデル
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           └── beans.xml
│   └── test/
└── build/
    └── libs/
        └── rs_greeting_service.war
```

## 🔧 使用している技術

- **Jakarta EE 10**
- **Payara Server 6**
- **Jakarta RESTful Web Services (JAX-RS) 3.1**
- **Jakarta JSON Binding (JSON-B)**
- **Jakarta CDI 4.0**

## 🛑 アプリケーションを停止する

### アプリケーションのアンデプロイ

```powershell
.\gradlew :projects:rs_greeting_service:undeploy
```

### Payara Server全体を停止

```powershell
.\gradlew stopPayara
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
