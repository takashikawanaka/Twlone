# Twlone(ポートフォリオ用)
![](https://img.shields.io/github/commit-activity/m/takashikawanaka/Twlone)  
このプロジェクトは、Twitterを模したSNSアプリケーションです。ユーザーは、短いメッセージを投稿し、他のユーザーの投稿を閲覧することができます。「いいね」や「シェア」などの機能があり、他のユーザーの投稿に対して反応することができます。また、投稿に画像を添付することもでき、メッセージをつなげて会話をすることも可能です。


## Todo
### 目標
- [x] ログイン機能
- [x] つぶやきの実装
- [x] ユーザのフォローフォロワー機能
- [x] いいね機能
- [ ] ユーザ画面
- [x] シェア、リプライ機能
- [x] 画像添付の実装
- [ ] つぶやき単体画面
- [ ] ハッシュタグの実装

### 追加実装予定
- [ ] アイコン・背景画像のアップロード
- [ ] 動画添付の実装
- [ ] メール送信によるユーザ登録の実装
- [ ] 一部テーブルをNoSQLへ変更

## DB設計
![ダウンロード](https://user-images.githubusercontent.com/123621760/229478168-bff6a582-5a82-42ce-a3e2-dfcfe80a6794.png)

## 使用技術
### 開発環境・サービス
Spring Tool Suite 4: 4.17.1.RELEASE  
Figma(大雑把なデザインを作る際に使用)  
InkScape(ロゴの作成、カーニング調整、SVGの出力の際)  
Bing Chat(質問によるJava・CSSのコード提案, エラーメッセージからの訂正提案, ダミーデータの作成, README作成アシスタント)  
Azure WebApp(アップロード予定)  

### サーバ
Java: 17.0.5  
Spring Boot: 2.7.9  
Spring Securiy: 5.7.7  
Apache Tomcat: 9.0.71

### データベース
MySQL: 8.0.30  
Hibernate: 5.6.15

### クライアント
Thymeleaf: 3.0.15  
[Tailwind CSS](https://tailwindcss.com): 3.2.7

### 開発支援ツール
Lombok: 1.18.26  
Spring Boot DevTools  
GitHub Actions

### 素材
[Material Icons](https://fonts.google.com/icons)(ボタンアイコン)  
[FLAT ICON DESIGN](http://flat-icon-design.com)(デフォルトユーザアイコン)  
[Pexels](https://www.pexels.com/de-de/suche/desktop%20hintergrundbilder/)(デフォルトユーザ背景画像)  
[SVG SILH](https://svgsilh.com/image/310517.html)(ロゴ素材)  
[Againts](https://pixelbuddha.net/fonts/free-font-againts-typeface)(ロゴフォント、ボタン)  
[いやすとや](https://www.irasutoya.com)(テスト用画像)

## 動作動画
### ユーザ登録からのログイン
https://user-images.githubusercontent.com/123621760/229481304-1efc3a74-accb-4022-a9c9-57e312a9edb1.mp4

