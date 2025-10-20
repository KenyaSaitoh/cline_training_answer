# Jakarta EE 10 Web Projects - Payara Server Edition

## 📖 概要

Jakarta EE 10とPayara Serverを使用したWebアプリケーションの学習プロジェクト集です。
全13プロジェクトで、Servlet/JSP、JSF、CDI、JAX-RSを段階的に学習できます。

## 📦 プロジェクト一覧

### Servlet系プロジェクト（3個）

| プロジェクト | 説明 |
|------------|------|
| `servlet_jsp` | ServletとJSPの基本 |
| `servlet_jsp_mvc` | Servlet + JSP MVC パターン |
| `servlet_session` | セッション管理とCookie |

### JSF系プロジェクト（3個）

| プロジェクト | 説明 |
|------------|------|
| `jsf_person` | JSFの基本（マネージドBean、Facelets） |
| `jsf_person_rdb` | JSF + JPA（データベース連携） |
| `jsf_ajax` | JSF Ajax機能 |

### CDI系プロジェクト（3個）

| プロジェクト | 説明 |
|------------|------|
| `cdi_inject` | CDI依存性注入の基本 |
| `cdi_conversation` | 会話スコープ（@ConversationScoped） |
| `cdi_transactional` | @Transactional（トランザクション管理） |

### RESTful Webサービス（4個）

| プロジェクト | 説明 |
|------------|------|
| `rs_hello_service` | JAX-RS Hello World |
| `rs_greeting_service` | JAX-RS CRUD操作 |
| `rs_employee_service` | JAX-RS + JPA（データベース連携） |
| `person_service` | RESTful API設計の実践 |

## 🚀 クイックスタート

### 前提条件

- **JDK 21以上**
- **Gradle 8.x以上**
- **Payara Server 6** (プロジェクトルートの`payara6/`に配置済み)
- **HSQLDB** (プロジェクトルートの`hsqldb/`に配置済み)

### 初回セットアップ

```powershell
# 1. 全プロジェクトをビルド
.\gradlew build

# 2. HSQLDBサーバーを起動（毎回必要）
.\gradlew startHsqldb

# 3. ドライバをコピー（初回のみ）
.\gradlew installHsqldbDriver

# 4. Payara Serverを起動（毎回必要）
.\gradlew startPayara

# 5. データソースを作成（初回のみ）
.\gradlew createConnectionPool
.\gradlew createDataSource

# 6. アプリケーションをデプロイ
.\gradlew :projects:servlet_jsp:deploy
```

**2回目以降の起動**：
```powershell
.\gradlew startHsqldb
.\gradlew startPayara
```

### アプリケーションへのアクセス

```
http://localhost:8080/servlet_jsp/PersonServlet?personName=Alice
```

### ログをリアルタイム監視（別のターミナル）

**Windows (PowerShell):**
```powershell
Get-Content -Path payara6\glassfish\domains\domain1\logs\server.log -Wait -Tail 50 -Encoding UTF8
```

**Linux/Mac:**
```bash
tail -f -n 50 payara6/glassfish/domains/domain1/logs/server.log
```

### サーバーの停止

```powershell
.\gradlew stopPayara
.\gradlew stopHsqldb
```

## 📋 Gradle タスク

### ビルドタスク

| タスク | 説明 |
|--------|------|
| `war` | WARファイルを作成 |
| `build` | プロジェクト全体をビルド |
| `clean` | ビルド成果物を削除 |

### Payara Serverタスク

| タスク | 説明 |
|--------|------|
| `startPayara` | Payara Serverを起動 |
| `stopPayara` | Payara Serverを停止 |
| `restartPayara` | Payara Serverを再起動 |
| `statusPayara` | Payara Serverのステータスを確認 |
| `killAllJava` | 全てのJavaプロセスを強制終了（緊急時用） |
| `setupDataSource` | HSQLDBデータソースをセットアップ（初回向け統合タスク） |
| `installHsqldbDriver` | HSQLDBドライバをPayara Serverにコピー（初回のみ） |
| `createConnectionPool` | JDBCコネクションプールを作成 |
| `createDataSource` | JDBCリソース（データソース）を作成 |
| `pingConnectionPool` | コネクションプールをテスト |
| `cleanupAll` | すべてをクリーンアップ（全アプリアンデプロイ＋データソース削除） |
| `undeployAllApps` | デプロイ済みの全アプリケーションをアンデプロイ |
| `deleteDataSource` | JDBCリソース（データソース）を削除 |
| `deleteConnectionPool` | JDBCコネクションプールを削除 |

### デプロイタスク（各プロジェクト）

| タスク | 説明 |
|--------|------|
| `deploy` | WARファイルをPayara Serverにデプロイ |
| `undeploy` | アプリケーションをアンデプロイ |

### データベースタスク

| タスク | 説明 |
|--------|------|
| `startHsqldb` | HSQLDB Databaseサーバーを起動 |
| `stopHsqldb` | HSQLDB Databaseサーバーを停止 |
| `setupHsqldb` | プロジェクト固有の初期データをセットアップ（各プロジェクト） |

## 🗄️ データベース設定

### HSQLDB接続情報

- **データベース名**: testdb
- **ユーザー名**: SA
- **パスワード**: （空文字）
- **TCPサーバー**: localhost:9001
- **JNDI名**: jdbc/HsqldbDS

接続設定は`env-conf.gradle`で管理されています。

### ターミナルからHSQLDBへ接続（SQLクライアント）

コマンドラインからSQLを実行する場合は、SqlToolを使用します：

**Windows (PowerShell):**
```powershell
java -cp "hsqldb\lib\hsqldb.jar;hsqldb\lib\sqltool.jar" org.hsqldb.cmdline.SqlTool --rcFile hsqldb\sqltool.rc testdb
```

**Linux/Mac:**
```bash
java -cp "hsqldb/lib/hsqldb.jar:hsqldb/lib/sqltool.jar" org.hsqldb.cmdline.SqlTool --rcFile hsqldb/sqltool.rc testdb
```

接続設定は`hsqldb/sqltool.rc`に記述されています。

**SQLの実行例:**

```sql
-- テーブル一覧を表示
\dt

-- テーブルの構造を確認
\d PERSON

-- データを確認
SELECT * FROM PERSON;

-- 終了
\q
```

## 🧹 環境のクリーンアップ

研修終了時に環境をクリーンアップするには：

```powershell
# すべてのアプリ、データソース、コネクションプールを削除
.\gradlew cleanupAll

# サーバーを停止
.\gradlew stopPayara
.\gradlew stopHsqldb
```

## 🔧 使用技術

| カテゴリ | 技術 | バージョン |
|---------|------|----------|
| **Java** | JDK | 21+ |
| **アプリケーションサーバー** | Payara Server | 6 |
| **Jakarta EE** | Platform | 10.0 |
| **Servlet** | Jakarta Servlet | 6.0 |
| **JSP** | Jakarta Server Pages | 3.1 |
| **JSF** | Jakarta Faces | 4.0 |
| **CDI** | Jakarta CDI | 4.0 |
| **JPA** | Jakarta Persistence | 3.1 |
| **JAX-RS** | Jakarta RESTful Web Services | 3.1 |
| **JSTL** | Jakarta Standard Tag Library | 3.0 |
| **データベース** | HSQLDB | 2.7.x |
| **ビルドツール** | Gradle | 8.x+ |

## 📚 ドキュメント

- **[設定ファイル](env-conf.gradle)** - Payara ServerとHSQLDB Database環境設定
- **各プロジェクトのREADME.md** - プロジェクト固有の詳細情報

## 🐛 トラブルシューティング

### Payara Serverが起動しない

Payara Serverのドメインステータスを確認：
```powershell
.\gradlew statusPayara
```

既存のドメインをクリーンアップして再起動：
```powershell
.\gradlew stopPayara
.\gradlew startPayara
```

プロセスが残っている場合（緊急時）：
```powershell
# 全てのJavaプロセスを強制終了（Gradleも含む）
.\gradlew killAllJava
```

### データベース接続エラー

1. HSQLDB Databaseサーバーが起動していることを確認：
```powershell
.\gradlew startHsqldb
```

2. データソースがセットアップされていることを確認：
```powershell
.\gradlew setupDataSource
.\gradlew pingConnectionPool
```

3. `env-conf.gradle`の接続情報を確認

### デプロイエラー

アプリケーションをアンデプロイしてから再デプロイ：
```powershell
.\gradlew :projects:servlet_jsp:undeploy
.\gradlew :projects:servlet_jsp:deploy
```

### ビルドエラー

クリーンビルドを実行：
```powershell
.\gradlew clean build
```
