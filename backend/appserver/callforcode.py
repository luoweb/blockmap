from selenium import webdriver
from time import sleep
import time
from PIL import ImageGrab
import numpy as np
import cv2
import datetime
from pynput import keyboard
import threading
import pyaudio
import wave
from moviepy.editor import *
from moviepy.audio.fx import all

# pip install --index https://pypi.mirrors.ustc.edu.cn/simple/ opencv-python Pillow 
# brew install portaudio 
# pip install --index https://pypi.mirrors.ustc.edu.cn/simple/ wheel pyaudio moviepy


def startChrome():
    options = webdriver.ChromeOptions()
    options.add_argument('lang=zh_CN.UTF-8')
    # options.add_argument("load-extension=/Users/luweb/Library/Application Support/Google/Chrome/Default/Extensions/liecbddmkiiihnedobmlmillhodjkdmb/3.3.96_0")
    # # 
    # extension_path=r"/Users/luweb/Library/Application Support/Google/Chrome/Default/Extensions/liecbddmkiiihnedobmlmillhodjkdmb/3.3.96_0"
    # options.add_extension(extension_path)
    # options.add_argument("user-data-dir=/Users/luweb/Library/Application Support/Google/Chrome/")
    # options.add_argument("load-extension=/Users/luweb/Library/Application Support/Google/Chrome/Default/Extensions/liecbddmkiiihnedobmlmillhodjkdmb/3.3.96_0")
    options.add_argument('User-Agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36')
    # options.add_argument('--flag-switches-begin --flag-switches-end --enable-audio-service-sandbox')  
    # options.add_argument('--disable-infobars')  # 禁止策略化
    # options.add_argument('--no-sandbox')  # 解决DevToolsActivePort文件不存在的报错
    # options.add_argument('window-size=1920x3000')  # 指定浏览器分辨率
    # options.add_argument('--disable-gpu')  # 谷歌文档提到需要加上这个属性来规避bug
    # options.add_argument('--incognito')  # 隐身模式（无痕模式）
    # options.add_argument('--disable-javascript')  # 禁用javascript
    # options.add_argument('--start-maximized')  # 最大化运行（全屏窗口）,不设置，取元素会报错
    # options.add_argument('--disable-infobars')  # 禁用浏览器正在被自动化程序控制的提示
    # options.add_argument('--hide-scrollbars')  # 隐藏滚动条, 应对一些特殊页面
    # options.add_argument('blink-settings=imagesEnabled=false')  # 不加载图片, 提升速度
    # options.add_argument('--headless')  # 浏览器不提供可视化页面. linux下如果系统不支持可视化不加这条会启动失败
    # options.binary_location = r"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"  # 手动指定使用的浏览器位置
    options.add_argument('--kiosk') # 谷歌浏览器全屏
    driver = webdriver.Chrome(options = options)
    driver.set_page_load_timeout(60)
    # driver.maximize_window()
    # broswer.set_window_size(1366,768)
    # 分辨率1366 x 768
    return driver




"""
python 屏幕录制改进版，无opencv黑框显示！
@zhou 2020/1/29_
"""

flag=False  #停止标志位
name = datetime.datetime.now().strftime('%Y-%m-%d %H-%M-%S') #当前的时间

def video_record():
    """
    屏幕录制！
    :return:
    """
    p = ImageGrab.grab()  # 获得当前屏幕
    a, b = p.size  # 获得当前屏幕的大小
    # fourcc = cv2.VideoWriter_fourcc(*'XVID')  # 编码格式
    # video = cv2.VideoWriter('%s.avi'%name, fourcc, 16, (a, b))  # 输出文件命名为test.mp4,帧率为16，可以自己设置
    
    fourcc = cv2.VideoWriter_fourcc(*'mp4v')  # 编码格式
    video = cv2.VideoWriter('%s.mov'%name, fourcc, 1, (a, b))  # 输出文件命名为test.mp4,帧率为16，可以自己设置

    print("video record start !!!")
    while True:
        im = ImageGrab.grab()
        imm=cv2.cvtColor(np.array(im), cv2.COLOR_RGB2BGR)#转为opencv的BGR格式
        video.write(imm)
        if flag:
            print("video record end !!!")
            break
    video.release()

def on_press(key):
    """
    键盘监听事件！！！
    :param key:
    :return:
    """
    #print(key)
    global flag
    if key == keyboard.Key.esc:
        flag=True
        # Esc Key sto Stop
        print("stop monitor！")
        task_stop()
        return False  #返回False，键盘监听结束！
 
def audio_record():
    """PyAudio example: Record a few seconds of audio and save to a WAVE file."""
    CHUNK = 1024
    FORMAT = pyaudio.paInt16
    CHANNELS = 1
    RATE = 8000
    RECORD_SECONDS = 3
    WAVE_OUTPUT_FILENAME ='%s.wav'%name

    p = pyaudio.PyAudio()

    stream = p.open(format=FORMAT,
                    channels=CHANNELS,
                    rate=RATE,
                    input=True,
                    frames_per_buffer=CHUNK)

    print("audio record start !!!")

    frames = []

    while True:
    # for i in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
        data = stream.read(CHUNK)
        frames.append(data)
        if flag:
            print("audio record end !!!")
            stream.stop_stream()
            stream.close()
            p.terminate()

            wf = wave.open(WAVE_OUTPUT_FILENAME, 'wb')
            wf.setnchannels(CHANNELS)
            wf.setsampwidth(p.get_sample_size(FORMAT))
            wf.setframerate(RATE)
            wf.writeframes(b''.join(frames))
            wf.close()  
            break

def audio_play():
    """PyAudio Example: Play a WAVE file."""

    CHUNK = 1024

    if len(sys.argv) < 2:
        print("Plays a wave file.\n\nUsage: %s filename.wav" % sys.argv[0])
        sys.exit(-1)

    wf = wave.open(sys.argv[1], 'rb')

    p = pyaudio.PyAudio()

    stream = p.open(format=p.get_format_from_width(wf.getsampwidth()),
                    channels=wf.getnchannels(),
                    rate=wf.getframerate(),
                    output=True)

    data = wf.readframes(CHUNK)

    while data != '':
        stream.write(data)
        data = wf.readframes(CHUNK)

    stream.stop_stream()
    stream.close()
    p.terminate()

def record_mp4():
    CHUNK = 1024
    FORMAT = pyaudio.paInt16
    CHANNELS = 2
    RATE = 44100
    WAVE_OUTPUT_FILENAME ='%s.wav'%name
    MOV_OUTPUT_FILENAME ='%s.mov'%name
    MP4_OUTPUT_FILENAME ='%s.mp4'%name

    p = pyaudio.PyAudio()
    wf = wave.open(WAVE_OUTPUT_FILENAME, 'wb')
    wf.setnchannels(CHANNELS)
    wf.setsampwidth(p.get_sample_size(FORMAT))
    wf.setframerate(RATE)
    audio_record_flag = True
    def callback(in_data, frame_count, time_info, status):
        wf.writeframes(in_data)
        if audio_record_flag:
            return (in_data, pyaudio.paContinue)
        else:
            return (in_data, pyaudio.paComplete)
    stream = p.open(format=p.get_format_from_width(wf.getsampwidth()),
        channels=wf.getnchannels(),
        rate=wf.getframerate(),
        input=True,
        stream_callback=callback)
    image = ImageGrab.grab()#获得当前屏幕
    width = image.size[0]
    height = image.size[1]
    print("width:", width, "height:", height)
    print("image mode:",image.mode)
    k=np.zeros((width,height),np.uint8)

    fourcc = cv2.VideoWriter_fourcc(*'XVID')#编码格式
    video = cv2.VideoWriter(MOV_OUTPUT_FILENAME, fourcc, 9.5, (width, height))
    #经实际测试，单线程下最高帧率为10帧/秒，且会变动，因此选择9.5帧/秒
    #若设置帧率与实际帧率不一致，会导致视频时间与音频时间不一致

    print("video recording!!!!!")
    stream.start_stream()
    print("audio recording!!!!!")
    record_count = 0
    while True:
        img_rgb = ImageGrab.grab()
        img_bgr=cv2.cvtColor(np.array(img_rgb), cv2.COLOR_RGB2BGR)#转为opencv的BGR格式
        video.write(img_bgr)
        record_count += 1
        if flag:
            print("mp4 record end !!!")
            break
        # if(record_count > 200):
        #     break
    print(record_count, time.time())

    audio_record_flag = False
    while stream.is_active():
        time.sleep(2)

    stream.stop_stream()
    stream.close()
    wf.close()
    p.terminate()
    print("audio recording done!!!!!")

    video.release()
    cv2.destroyAllWindows()
    print("video recording done!!!!!")

    print("video audio merge!!!!!")
    audioclip = AudioFileClip(WAVE_OUTPUT_FILENAME)
    videoclip = VideoFileClip(MOV_OUTPUT_FILENAME)
    # videoclip = VideoFileClip(MOV_OUTPUT_FILENAME,audio=True)
    videoclip2 = videoclip.set_audio(audioclip)
    # video = CompositeVideoClip([videoclip2])
    videoclip2.write_videofile(MP4_OUTPUT_FILENAME,codec='mpeg4')


def task_stop():
    sleep(5)
    driver.close()
    driver.quit()
   
if __name__=='__main__':
    # driver.manage().window().maximize()
    print("Chrome start")
    driver = startChrome()
    url = "https://player.twitch.tv/?volume=1&!muted&channel=ibmdeveloper"
    # url = "https://player.twitch.tv/?volume=1&channel=ibmdeveloper"
    url = "https://www.bilibili.com/video/BV1S4411w717?from=search&seid=789350787193747000"

    driver.get(url)

    # sleep(2)
    # driver.find_element_by_css_selector(".pl-close-button > figure").click()
    # sleep(2)
    # driver.find_element_by_css_selector(".pl-close-button > figure").click()
    # sleep(2)
    # driver.find_element_by_css_selector(".qa-pause-play-button").click()
    # sleep(2) #等待页面加载
    # driver.find_element_by_xpath('//*[@id="TANGRAM__PSP_4__footerULoginBtn"]').click() #选择账号密码登录
    # sleep(2)
    # driver.find_element_by_name("userName").send_keys("1142903706@qq.com") #输入账户密码
    # driver.find_element_by_name("password").send_keys("1142903706")
    # driver.find_element_by_xpath('//*[@id="TANGRAM__PSP_4__submit"]').click() #登录
    # 先说一下selenium 的定位方法

    # find_element_by_id 
    # find_element_by_name
    # find_element_by_xpath
    # find_element_by_link_text
    # find_element_by_partial_link_text
    # find_element_by_tag_name
    # find_element_by_class_name
    # find_element_by_css_selector
    # 前八种是大家都熟悉的，经常会用到的

    # 1.id定位：find_element_by_id(self, id_)
    # 2.name定位：find_element_by_name(self, name)
    # 3.class定位：find_element_by_class_name(self, name)
    # 4.tag定位：find_element_by_tag_name(self, name)
    # 5.link定位：find_element_by_link_text(self, link_text)
    # 6.partial_link定位find_element_by_partial_link_text(self, link_text)
    # 7.xpath定位：find_element_by_xpath(self, xpath)
    # 8.css定位：find_element_by_css_selector(self, css_selector）
    # sleep(5)
    # driver.close()
    # driver.quit()

    # th1=threading.Thread(target=video_record)
    # th1.start()
    # th2=threading.Thread(target=audio_record)
    # th2.start()
    th3=threading.Thread(target=record_mp4)
    th3.start()
    
    with keyboard.Listener(on_press=on_press) as listener:
        listener.join()


