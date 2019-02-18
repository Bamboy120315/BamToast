## BamToast：

	名词介绍：  
	  
	BToast：  
	本人之前写的自定义Toast，  
	与原生Toast一样可以全局显示。  
	  
	EToast：  
	网上一个大神写的，  
	可以在没有通知权限的情况下弹出的Toast。  
	  
	BamToast：  
	EToast + BToast。  
	根据当前通知权限状态自动选择，  
	如果当前有通知权限就用BToast，  
	没有就使用EToast。  
  
正常Toast在用户关闭了通知权限后就无法弹出了，  
BamToast解决了这个问题，  
方案采用网上的EToast + BToast。  
  
实现很简单，  
判断通知权限，  
如果有通知权限，  
就正常使用BToast，  
离开Activity也可以全局显示，  
否则使用EToast，  
保障用户能够看到Toast。  
  
至于EToast在未打开通知权限也能弹出的原理，  
其实很简单，  
通知权限未获取时，  
Toast其实还是正常绘制的，  
只是由于权限问题无法显示而已，  
EToast则是通过getView()，  
获取了绘制但未显示的Toast，  
然后将其在Activity中显示出来。  
  
那么和原本的Toast有什么区别呢？  
  
1、原生Toast在显示和退出时有渐变动画，  
EToast没有，  
所以视觉上稍稍欠佳，  
不过没事，  
BamToast的图标是有动画的，  
一定程度上有所弥补。  
  
2、原生的Toast是系统级的，  
所以Activity离开也能正常显示，  
而EToast是基于Activity的，  
所以若Activity离开，  
Toast也会随之离开。  
  
3、因为EToast是基于Activity的，  
所以必须要使用本类的Context,  
不能使用getApplicationContext()，  
切记！  