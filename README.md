# Twlone(ポートフォリオ用)
![](https://img.shields.io/github/commit-activity/w/takashikawanaka/Twlone)  
このプロジェクトは、Twitterを模したSNSアプリケーションです。ユーザーは、短いメッセージを投稿し、他のユーザーの投稿を閲覧することができます。「いいね」や「シェア」などの機能があり、他のユーザーの投稿に対して反応することができます。また、投稿に画像を添付することもでき、メッセージをつなげて会話をすることも可能です。

## Git Branch
[Mysql](https://github.com/takashikawanaka/Twlone/tree/mysql) バージョン / [Elasticsearch](https://github.com/takashikawanaka/Twlone/tree/elastic) バージョン

## 実行周り
### テストに使用しているユーザー
ID:`test`, Pass:`ktaro`

### クローン後の初回起動時のエラー
```
***************************
APPLICATION FAILED TO START
***************************

Description:

Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.

Reason: Failed to determine a suitable driver class


Action:

Consider the following:
	If you want an embedded database (H2, HSQL or Derby), please put it on the classpath.
	If you have database settings to be loaded from a particular profile you may need to activate it (no profiles are currently active).
```
pom.xmlの下記の項目を、一度取り除いて実行してから、元に戻す
``` xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```


### CSSの解決(どちらか一方)
`/src/main/resources/templates/fragment/head.html`の修正
``` HTML
<!-- アンコメント
<link th:href="@{/css/input.css}" rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>
-->
```
`/css/output.css`を使う場合
```
npx tailwindcss -i .\src\main\resources\static\css\input.css -o .\src\main\resources\static\css\output.css
```
### ElasticSearch [ElasticBranch](https://github.com/takashikawanaka/Twlone/tree/elastic)
#### 環境構築 [Reference](https://www.elastic.co/guide/en/elasticsearch/reference/8.7/docker.html)
<details>
<summary>.env</summary>

``` env
ELASTIC_PASSWORD=password
KIBANA_PASSWORD=password
STACK_VERSION=8.7.0
CLUSTER_NAME=docker-cluster
LICENSE=basic
ES_PORT=9200
KIBANA_PORT=5601
MEM_LIMIT=1073741824
```

</details>

<details>
<summary>docker-compose.yml</summary>

``` yml
version: "2.2"
services:
  es01:
    image: docker.elastic.co/elasticsearch/elasticsearch:${STACK_VERSION}
    volumes:
      - esdata01:/usr/share/elasticsearch/data
    ports:
      - ${ES_PORT}:9200
    environment:
      - node.name=es01
      - cluster.name=${CLUSTER_NAME}
      - cluster.initial_master_nodes=es01,es02,es03
      - discovery.seed_hosts=es02,es03
      - ELASTIC_PASSWORD=${ELASTIC_PASSWORD}
      - bootstrap.memory_lock=true
      - xpack.security.enabled=false
      - xpack.license.self_generated.type=${LICENSE}
    mem_limit: ${MEM_LIMIT}
    ulimits:
      memlock:
        soft: -1
        hard: -1

  es02:
    depends_on:
      - es01
    image: docker.elastic.co/elasticsearch/elasticsearch:${STACK_VERSION}
    volumes:
      - esdata02:/usr/share/elasticsearch/data
    environment:
      - node.name=es02
      - cluster.name=${CLUSTER_NAME}
      - cluster.initial_master_nodes=es01,es02,es03
      - discovery.seed_hosts=es01,es03
      - bootstrap.memory_lock=true
      - xpack.security.enabled=false
      - xpack.license.self_generated.type=${LICENSE}
    mem_limit: ${MEM_LIMIT}
    ulimits:
      memlock:
        soft: -1
        hard: -1

  es03:
    depends_on:
      - es02
    image: docker.elastic.co/elasticsearch/elasticsearch:${STACK_VERSION}
    volumes:
      - esdata03:/usr/share/elasticsearch/data
    environment:
      - node.name=es03
      - cluster.name=${CLUSTER_NAME}
      - cluster.initial_master_nodes=es01,es02,es03
      - discovery.seed_hosts=es01,es02
      - bootstrap.memory_lock=true
      - xpack.security.enabled=false
      - xpack.license.self_generated.type=${LICENSE}
    mem_limit: ${MEM_LIMIT}
    ulimits:
      memlock:
        soft: -1
        hard: -1

  kibana:
    depends_on:
      - es01
      - es02
      - es03
    image: docker.elastic.co/kibana/kibana:${STACK_VERSION}
    volumes:
      - kibanadata:/usr/share/kibana/data
    ports:
      - ${KIBANA_PORT}:5601
    environment:
      - SERVERNAME=kibana
      - ELASTICSEARCH_HOSTS=http://es01:9200
    mem_limit: ${MEM_LIMIT}

volumes:
  esdata01:
    driver: local
  esdata02:
    driver: local
  esdata03:
    driver: local
  kibanadata:
    driver: local
```

</details>

#### ランダムなつぶやき
``` java
//@EnableScheduling  アンコメント
@SpringBootApplication
public class TwloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(TwloneApplication.class, args);
    }
}
```

## HashTag規則
ハッシュタグ : `#abc`, `#ABC`, `#2023年`, `#アイウ`, `#日本語`, `#LOL😂`, `#Hello_World`, `#New_Hello_World`, `#test!?`, `#test?test!`,  
`#ONE` `#FOR` `#ALL`    
ハッシュタグではない : `#1`, `#A`, `#123`,`# ABC`, `##ABC`, `＃aa`, `#_test`, `#!?2023年`, `#!?_`  

## Todo
### 目標
- [x] ログイン機能
- [x] つぶやきの実装
- [x] ユーザのフォローフォロワー機能
- [x] いいね機能
- [x] ユーザ画面
- [x] シェア、リプライ機能
- [x] 画像添付の実装
- [x] つぶやき単体画面
- [x] ハッシュタグの実装
- [x] 検索の実装 
- [x] プロフィールの編集機能
- [x] ホームタイムラインの実装

### 追加実装予定
- [ ] アイコン・背景画像のアップロード
- [x] TwテーブルをElasticSearchへ変更
- [x] Twテーブルに付随するテーブルをElasticSearchへ変更
- [ ] Icon及び背景画像の拡大表示
- [ ] 動画添付の実装
- [x] URLテーブルの実装・表示の実装
- [ ] メール送信によるユーザ登録の実装

## DB設計
<details>
<summary>sql</summary>

![twitter drawio](https://user-images.githubusercontent.com/123621760/236760782-1b1ff385-4d1a-40e8-b239-963651e4d22d.png)

</details>

<details>
<summary>sql + elasticsearch</summary>

![ダウンロード (1)](https://user-images.githubusercontent.com/123621760/236760811-1a32814a-fbe3-4235-a94e-9fe8e8348423.png)

</details>

## 使用技術
### 開発環境・サービス
Spring Tool Suite 4: 4.17.1.RELEASE  
Figma(大雑把なデザインを作る際に使用)  
InkScape(ロゴの作成、カーニング調整、SVGの出力)  
Microsoft Edge
Bing Chat(質問によるJava・CSSのコード提案, エラーメッセージからの訂正提案, ダミーデータの作成, README作成アシスタント)  
Azure WebApp(アップロード予定)  

### サーバ
Java: 17.0.5  
Spring Boot: 2.7.9  
Spring Securiy: 5.7.7  
Apache Tomcat: 9.0.71
Awaitility: 4.2.0 (自動的に投稿するため)

### データベース
MySQL: 8.0.30  
Hibernate: 5.6.15  
Elasticsearch: 8.7.0  
Spring Data Elasticsearch: 4.4.11  

### クライアント
Thymeleaf: 3.0.15  
[Tailwind CSS](https://tailwindcss.com): 3.2.7

### 開発支援ツール
Lombok: 1.18.26  
Spring Boot DevTools  
Docker Desktop: 4.19.0   
GitHub Actions

### 素材
[Material Icons](https://fonts.google.com/icons)(ボタンアイコン)  
[FLAT ICON DESIGN](http://flat-icon-design.com)(デフォルトユーザアイコン)  
[Pexels](https://www.pexels.com/de-de/suche/desktop%20hintergrundbilder/)(デフォルトユーザ背景画像)  
[SVG SILH](https://svgsilh.com/image/310517.html)(ロゴ素材)  
[Againts](https://pixelbuddha.net/fonts/free-font-againts-typeface)(ロゴフォント、ボタン)  
[いやすとや](https://www.irasutoya.com)(テスト用画像)

## 動作動画
| ユーザ登録からのログイン | ハッシュタグ付きつぶやき | フォローアンフォロー機能 |
| ------------- | ------------- | ------------- |
| <img src="https://user-images.githubusercontent.com/123621760/232187472-f2b0fabb-30ea-4478-ba7f-9f7aa90ffb4d.gif"> | <img src="https://user-images.githubusercontent.com/123621760/232189688-c3259114-4408-449b-9bae-2795d94a06b0.gif"> | <img src="https://user-images.githubusercontent.com/123621760/232190095-656a7eec-9afd-45bf-8a65-444d6437436f.gif"> |

| いいね機能 | シェア機能 | リプライ機能 |
| ------------- | ------------- | ------------- | 
| <img src="https://user-images.githubusercontent.com/123621760/232190425-d80ebe9c-1f75-48ae-b98c-324e687377c4.gif"> | <img src="https://user-images.githubusercontent.com/123621760/232190796-f3d5dbd4-e044-4439-a00d-4d7b29220fec.gif"> | <img src="https://user-images.githubusercontent.com/123621760/232190501-bca141fd-8833-4db8-9a50-4ba133041662.gif"> |

| 画像添付機能 | NULL機能 | NULL機能 |
| ------------- | ------------- | ------------- | 
| <img src="https://user-images.githubusercontent.com/123621760/232190916-c0287466-008e-4341-a065-abc77523899b.gif"> |  |  |

