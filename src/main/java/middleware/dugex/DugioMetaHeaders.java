/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
class DugioMetaHeaders {
                                                /*
                                                Class of MetaHeaders only. 
                                                */
     
      static String timeStamp;
      static String subsurface;
     //String traceCount="_HDR:P_MAX_FOLD";   //No longer found in the metavalues! Use dugio summary and grep to extract
      static String inlineMax="_HDR:INLINE_MAX";
      static String inlineMin="_HDR:INLINE_MIN";
      static String inlineInc="_HDR:INLINE_INC";
      static String xlineMax="_HDR:CROSSLINE_MAX";
      static String xlineMin="_HDR:CROSSLINE_MIN";
      static String xlineInc="_HDR:CROSSLINE_INC";
      static String dugShotMax="_HDR:SHOT_MAX";
      static String dugShotMin="_HDR:SHOT_MIN";
      static String dugShotInc="_HDR:SHOT_INC";
      static String dugChannelMax="_HDR:CHANNEL_MAX";
      static String dugChannelMin="_HDR:CHANNEL_MIN";
      static String dugChannelInc="_HDR:CHANNEL_INC";
      static String offsetMax="_HDR:OFFSET_MAX";
      static String offsetMin="_HDR:OFFSET_MIN";
      static String offsetInc="_HDR:OFFSET_INC";
      static String cmpMax="_HDR:CMP_MAX";
      static String cmpMin="_HDR:CMP_MIN";
      static String cmpInc="_HDR:CMP_INC";

      static String[] metaHeaders={inlineMax,inlineMin,inlineInc,xlineMax,xlineMin,xlineInc,dugShotMax,dugShotMin,dugShotInc,dugChannelMax,dugChannelMin,dugChannelInc,offsetMax,offsetMin,offsetInc,cmpMax,cmpMin,cmpInc};

}
