# cdi_inject プロジェクト

## 📖 概要

CDI (Contexts and Dependency Injection) の基本的な依存性注入を学ぶサンプルです。
@Inject、@Named、スコープなどの基本概念を学習できます。

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
.\gradlew :projects:cdi_inject:war
```

### 3. アプリケーションのデプロイ（アプリケーション作成・更新のたびに実施）

```powershell
.\gradlew :projects:cdi_inject:deploy
```

## 📍 アクセスURL

デプロイ後、以下のURLにアクセス：

- **トップページ**: http://localhost:8080/cdi_inject/

## 🎯 プロジェクト構成

```
projects/cdi_inject/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── bean/              # CDI Bean
│   │   │   ├── service/           # サービスクラス
│   │   │   └── servlet/           # Servlet
│   │   └── webapp/
│   │       ├── index.html
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           └── beans.xml      # CDI設定
│   └── test/
└── build/
    └── libs/
        └── cdi_inject.war
```

## 🔧 使用している技術

- **Jakarta EE 10**
- **Payara Server 6**
- **Jakarta CDI 4.0**
- **Jakarta Servlet 6.0**

## 🛑 アプリケーションを停止する

### アプリケーションのアンデプロイ

```powershell
.\gradlew :projects:cdi_inject:undeploy
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
