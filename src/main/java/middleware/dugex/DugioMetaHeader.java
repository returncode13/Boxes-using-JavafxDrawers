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
public class DugioMetaHeader {
     String timeStamp;
     String subsurface;
     //String traceCount="_HDR:P_MAX_FOLD";   //No longer found in the metavalues! Use dugio summary and grep to extract
     String inlineMax="_HDR:INLINE_MAX";
     String inlineMin="_HDR:INLINE_MIN";
     String inlineInc="_HDR:INLINE_INC";
     String xlineMax="_HDR:CROSSLINE_MAX";
     String xlineMin="_HDR:CROSSLINE_MIN";
     String xlineInc="_HDR:CROSSLINE_INC";
     String dugShotMax="_HDR:SHOT_MAX";
     String dugShotMin="_HDR:SHOT_MIN";
     String dugShotInc="_HDR:SHOT_INC";
     String dugChannelMax="_HDR:CHANNEL_MAX";
     String dugChannelMin="_HDR:CHANNEL_MIN";
     String dugChannelInc="_HDR:CHANNEL_INC";
     String offsetMax="_HDR:OFFSET_MAX";
     String offsetMin="_HDR:OFFSET_MIN";
     String offsetInc="_HDR:OFFSET_INC";
     String cmpMax="_HDR:CMP_MAX";
     String cmpMin="_HDR:CMP_MIN";
     String cmpInc="_HDR:CMP_INC";

     String[] metaHeaders={inlineMax,inlineMin,inlineInc,xlineMax,xlineMin,xlineInc,dugShotMax,dugShotMin,dugShotInc,dugChannelMax,dugChannelMin,dugChannelInc,offsetMax,offsetMin,offsetInc,cmpMax,cmpMin,cmpInc};
}
