# pWeb3j(platon extension for web3j)

[PlatON](https://github.com/PlatONnetwork/PlatON-Go)网络是一个以太坊兼容网络，除了以太坊的标准接口外，还有自己特有的东西。

- 新增rpc协议
- 内置合约, 区别于solidity合约，存在自己的编解码。
- 特点的地址格式展示
- wasm合约支持

[SPlatON](https://github.com/PlatONnetwork/PlatON-Go/tree/special-platon-develop)节点区别于PlatON节点，增加了经济模型历史数据保存及查询功能，交易账号批量查询接口，主用用于区块链浏览器

[Web3j](https://github.com/web3j/web3j)是连接以太坊使用比较多的sdk。主要用于java 和 android语言的开发。

pWeb3j作为PlatON的sdk, 依赖于Web3j，提供PlatON网络特有的支持。目前主要功能模块如下

1. platon模块,用于对PlatON节点特有协议的支持。主要包含三部分。
   - protocol包: 新增rpc协议的扩展
   - contracts包: 内置合约的支持，包括与合约交互，合约交易数据编码等
   - utils包: 包括lat格式地址转换，区块中节点id计算等

2. splaton模块依赖于platon模块, 用于对SPlatON节点特有协议的支持

pWeb3j项目仅仅依赖于Web3j

# 导入

Maven

```xml
<dependency>
  <groupId>io.github.chenxiaodai.web3j</groupId>
  <artifactId>platon</artifactId>
  <version>0.0.3</version>
</dependency>
```

Gradle

```groovy
implementation ('io.github.chenxiaodai.web3j:platon:0.0.3')
```

# 使用简介

### 内置合约

`发起委托交易`:

```java
Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Credentials deleteCredentials = Credentials.create("0x...");
long chainId = 2022041902;
String nodeId = "0x...";
StakingContract stakingContract = StakingContract.load(web3j, deleteCredentials,  chainId);
TransactionResponse response = stakingContract.delegate(nodeId, DelegateAmountTypeEnum.BALANCE, Convert.toWei("100", Convert.Unit.ETHER).toBigInteger()).send();
System.out.println(response);
```

`委托交易data编码`:

```java
String nodeId = "0x...";
String data = StakingContract.encodeTransactionDataOfDelegate(nodeId, DelegateAmountTypeEnum.BALANCE, Convert.toWei("100", Convert.Unit.ETHER).toBigInteger());
System.out.println(response);
```

### 协议

`获得节点版本`:

```java
PlatON web3j = PlatON.build(new HttpService("http://127.0.0.1:6789"));
AdminProgramVersion.ProgramVersion programVersion = web3j.getProgramVersion().send().getResult();
```

### 工具

`地址转换`:

```java
String latAddress = Bech32Utils.encodeWithLat("0xadb991bf18a0d930b538d6c24773f6b90dba4109");
String hexAddress = Bech32Utils.decode("lat14kuer0cc5rvnpdfc6mpywulkhyxm5sgfema80l");
```
