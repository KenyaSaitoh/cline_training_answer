# servlet_jsp プロジェクト

## 📖 概要

Jakarta ServletとJSP (Jakarta Server Pages) の基本を学ぶサンプルプロジェクトです。
サーブレット、JSPの連携、フォーム処理を学習できます。

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
.\gradlew :projects:servlet_jsp:war
```

### 3. アプリケーションのデプロイ（アプリケーション作成・更新のたびに実施）

```powershell
.\gradlew :projects:servlet_jsp:deploy
```

## 📍 アクセスURL

デプロイ後、以下のURLにアクセス：

- **トップページ**: http://localhost:8080/servlet_jsp/PersonServlet?personName=Alice

## 🎯 プロジェクト構成

```
projects/servlet_jsp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── servlet/           # Servletクラス
│   │   └── webapp/
│   │       ├── index.html
│   │       ├── WEB-INF/
│   │       │   ├── web.xml
│   │       │   └── jsp/           # JSPファイル
│   │       └── css/               # スタイルシート
│   └── test/
└── build/
    └── libs/
        └── servlet_jsp.war
```

## 🔧 使用している技術

- **Jakarta EE 10**
- **Payara Server 6**
- **Jakarta Servlet 6.0**
- **Jakarta Server Pages (JSP) 3.1**
- **JSTL 3.0**

## 🛑 アプリケーションを停止する

### アプリケーションのアンデプロイ

```powershell
.\gradlew :projects:servlet_jsp:undeploy
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
