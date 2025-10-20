# jsf_person プロジェクト

## 📖 概要

JavaServer Faces (JSF)の基本的な使い方を学ぶサンプルプロジェクトです。
マネージドBean、Facelets、コンポーネントベースのWeb開発を学習できます。

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
.\gradlew :projects:jsf_person:war
```

### 3. アプリケーションのデプロイ（アプリケーション作成・更新のたびに実施）

```powershell
.\gradlew :projects:jsf_person:deploy
```

## 📍 アクセスURL

デプロイ後、以下のURLにアクセス：

- **Person入力画面**: http://localhost:8080/jsf_person/faces/PersonInputPage.xhtml

## 🎯 プロジェクト構成

```
projects/jsf_person/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── bean/
│   │   │       └── PersonBean.java    # マネージドBean
│   │   └── webapp/
│   │       ├── *.xhtml                 # Faceletsページ
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           ├── beans.xml           # CDI設定
│   │           └── faces-config.xml    # JSF設定
│   └── test/
└── build/
    └── libs/
        └── jsf_person.war
```

## 🔧 使用している技術

- **Jakarta EE 10**
- **Payara Server 6**
- **Jakarta Faces (JSF) 4.0**
- **Jakarta CDI 4.0**
- **Facelets**

## 🛑 アプリケーションを停止する

### アプリケーションのアンデプロイ

```powershell
.\gradlew :projects:jsf_person:undeploy
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
