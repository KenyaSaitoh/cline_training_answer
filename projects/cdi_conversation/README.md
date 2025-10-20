# cdi_conversation プロジェクト

## 📖 概要

CDIの会話スコープ（@ConversationScoped）を学ぶサンプルプロジェクトです。
複数画面にまたがる状態管理を学習できます。

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
.\gradlew :projects:cdi_conversation:war
```

### 3. アプリケーションのデプロイ（アプリケーション作成・更新のたびに実施）

```powershell
.\gradlew :projects:cdi_conversation:deploy
```

## 📍 アクセスURL

デプロイ後、以下のURLにアクセス：

- **トップページ**: http://localhost:8080/cdi_conversation/faces/index.xhtml

## 🎯 プロジェクト構成

```
projects/cdi_conversation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── bean/              # CDI Bean (@ConversationScoped)
│   │   └── webapp/
│   │       ├── *.xhtml
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           └── beans.xml
│   └── test/
└── build/
    └── libs/
        └── cdi_conversation.war
```

## 🔧 使用している技術

- **Jakarta EE 10**
- **Payara Server 6**
- **Jakarta CDI 4.0**
- **Jakarta Faces (JSF) 4.0**
- **@ConversationScoped**

## 🛑 アプリケーションを停止する

### アプリケーションのアンデプロイ

```powershell
.\gradlew :projects:cdi_conversation:undeploy
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
