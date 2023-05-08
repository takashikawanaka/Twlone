package com.twlone;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.function.Supplier;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.twlone.entity.ETw;
import com.twlone.service.ElasticsearchOperationsWapper;

@Component
public class ScheduledTasks {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final ElasticsearchOperationsWapper operations;

    public ScheduledTasks(ElasticsearchOperationsWapper operations) {
        this.operations = operations;
    }

    @Scheduled(cron = "0 */15 * * * *")
    public void reportCurrentTime() {
        ETw eTw = new ETw(23);
        eTw.InitETw();
        eTw.setContent("The time is now " + dateFormat.format(new Date()));
        operations.saveTw(eTw);
    }

    @Scheduled(cron = "* * * * * *")
    public void userTwTime() {
        Random random = new Random();
        ETw eTw = new ETw(random.nextInt(18) + 3);
        eTw.InitETw();
        eTw.setContent(((Supplier<String>) () -> {
            String[] subject = { "犬", "猫", "魚", "鳥", "馬", "牛", "豚", "羊", "蛇", "熊", "虎", "象", "鹿", "狼", "猿", "兎", "鼠",
                    "蝶々", "蜜蜂", "蚊", "蟻", "蜘蛛", "蛙", "亀", "鰐", "竜", "鳳凰", "麒麟", "独角獣", "妖精", "妖怪", "天使", "悪魔", "神様",
                    "仏様", "りんご", "みかん", "ぶどう", "いちご", "バナナ", "メロン", "スイカ", "桃", "梨", "さくらんぼ", "キウイ", "パイナップル", "マンゴー",
                    "レモン", "ライム", "オレンジ", "グレープフルーツ", "トマト", "きゅうり", "なす", "ピーマン", "とうもろこし", "じゃがいも", "さつまいも", "にんじん",
                    "大根", "玉ねぎ", "にんにく", "生姜", "ブロッコリー", "寿司", "天ぷら", "うどん", "そば", "ラーメン", "焼きそば", "お好み焼き", "たこ焼き",
                    "餃子", "カレーライス", "牛丼", "親子丼", "カツ丼", "天丼", "鰻丼", "海鮮丼", "おにぎり", "弁当", "炊き込みご飯", "茶碗蒸し", "おでん",
                    "すき焼き", "しゃぶしゃぶ", "焼肉", "てっちゃん焼き", "フィッシュ・アンド・チップス", "ローストビーフ", "ビーフ・ウェリントン", "シェパーズパイ", "コテージパイ",
                    "トフィープディング", "バブル・アンド・スクイーク", "ブラックプディング", "ハギス", "コンフィット・オブ・ダック", "スコッチエッグ", "ブイヤベース", "カスレ",
                    "クレープ", "クロックムッシュ", "クロックマダム", "コキーユ・サン・ジャック", "コンフィ・ド・カナール", "コート・ド・ブフ・ブルギニョン", "シュークルート",
                    "タルト・タタン", "キッシュ・ロレーヌ", "ピザ", "パスタ", "ラザニア", "スパゲッティ・ボロネーゼ", "フェットチーネ・アルフレード", "カルボナーラ", "ペスカトーレ",
                    "オッソブーコ", "ミラノ風カツレツ", "リゾット", "ミネストローネ", "北京ダック", "麻婆豆腐", "回鍋肉", "宮保鶏丁", "棒棒鶏", "酢豚", "八宝飯", "炒飯",
                    "炒麺", "餃子", "湯包", "キムチ", "ビビンバ", "プルコギ", "サムギョプサル", "チヂミ", "トッポッキ", "チャプチェ", "カルビ", "スンドゥブチゲ",
                    "キムチチゲ", "プデチゲ", "パエリア", "ガスパチョ", "トルティージャ", "フラメンキン", "クロケータス", "ポテト・ブラバス", "カルド・ガジョ",
                    "ピンチョス・モロノス", "ポルロ・アル・アヒージョ", "ファバーダ", "エンサラダ・ムルシアーナ", "ザワークラウト", "ヴィンナーシュニッツェル", "カルテッフェルプッフェル",
                    "シュペッツレ", "ブレッツェン", "カイザーシュマーレン", "シュトーレン", "シュヴァインシャクセ", "ラプスカウス", "フリカデレ", "ブラットヴルスト", "唐揚げ",
                    "アジフライ", "タピオカ", "パンケーキ" };
            String[] particle = { "が", "を", "に", "へ", "の", "と", "から", "より", "で" };
            String[] predicate = { "好き", "大好き", "嫌い", "美味しい", "マイブーム", "眠い", "痛い", "熱い", "楽しい", "悲しい", "怒り", "驚き", "恐れ", "期待",
                    "安心", "不安", "幸せ", "悔しい", "切ない", "憧れ", "羨ましい", "食べる", "飲む", "見る", "聞く", "話す", "書く", "読む", "歩く", "走る",
                    "泳ぐ", "飛ぶ", "跳ぶ", "踊る", "歌う", "笑う", "泣く", "叫ぶ", "怒る", "喜ぶ", "悲しむ", "驚く", "大きい", "小さい", "長い", "短い",
                    "高い", "低い", "広い", "狭い", "厚い", "薄い", "重い", "軽い", "暑い", "寒い", "温かい", "冷たい", "新しい", "古い", "若い", "老けた",
                    "美しい" };
            String generate = subject[random.nextInt(subject.length)] + particle[random.nextInt(particle.length)]
                    + predicate[random.nextInt(predicate.length)];
            return generate;
        }).get());
        operations.saveTw(eTw);
    }
}
