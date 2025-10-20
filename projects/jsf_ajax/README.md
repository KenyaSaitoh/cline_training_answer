# jsf_ajax プロジェクト

## 📖 概要

JSFのAjax機能を使用した非同期通信のサンプルです。
ページ全体をリロードせずに、部分的な更新を実現する方法を学習できます。

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
.\gradlew :projects:jsf_ajax:war
```

### 3. アプリケーションのデプロイ（アプリケーション作成・更新のたびに実施）

```powershell
.\gradlew :projects:jsf_ajax:deploy
```

## 📍 アクセスURL

デプロイ後、以下のURLにアクセス：

- **Ajax デモ**: http://localhost:8080/jsf_ajax/faces/index.xhtml

## 🎯 プロジェクト構成

```
projects/jsf_ajax/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── bean/              # マネージドBean
│   │   └── webapp/
│   │       ├── *.xhtml
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           └── beans.xml
│   └── test/
└── build/
    └── libs/
        └── jsf_ajax.war
```

## 🔧 使用している技術

- **Jakarta EE 10**
- **Payara Server 6**
- **Jakarta Faces (JSF) 4.0**
- **JSF Ajax (f:ajax)**

## 🛑 アプリケーションを停止する

### アプリケーションのアンデプロイ

```powershell
.\gradlew :projects:jsf_ajax:undeploy
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
