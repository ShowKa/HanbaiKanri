
# 前提

- Beanstalkの実行ロールにDockerRepository (ECR)の読み取り権限を追加しておくこと。

# デプロイ

- アプリケーションのDocker image のURLを追記します。
- 上記のファイルとフォルダの .zip アーカイブを作成します (最上位プロジェクトフォルダを含みません)。
- zip を AWS Elastic Beanstalk に アップロードします。

