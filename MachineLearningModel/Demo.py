
# Imports
import tensorflow as tf

# Object detection imports
from utils import backbone
from api import object_counting_api

if tf.__version__ < '1.4.0':
  raise ImportError('Please upgrade your tensorflow installation to v1.4.* or later!')

input_video = "Hackthon.MOV"

#Detection model zoo 
detection_graph, category_index = backbone.set_model('ssd_mobilenet_v1_coco_2017_11_17')

targeted_objects = "person"
fps = 31.580
width = 1920 
height = 1080
is_color_recognition_enabled = 0

object_counting_api.cumulative_object_counting_x_axis(input_video, detection_graph, category_index, is_color_recognition_enabled, fps, width, height, 700) # 400 is the x location of the ROI Line
