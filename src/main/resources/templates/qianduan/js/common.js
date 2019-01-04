var _dr = {
    url: '',
    host: '',
    query: '',
    referrer: '',
    source: '',
    medium : '',
    seo: '',
    channel: '',
    queryparams: new Object(),
    sourceflag: '',
    queryattr: 'dr-aws',
    orderkey: '_dr-aws-orderkey',
    pvidkey: '_dr-aws-pvid',
    uvidkey: '_dr-aws-uvid',
    sourceflagkey: '_dr-aws-sourceflag',
    params: {},
    isinit: false,

    _init: function() {
        this.params.version = '0.1';
        if (document) {
            this.url = document.URL || '';
            this.referrer = document.referrer || '';
            this.params.domain = document.domain || '';
            this.params.url = this.url;
            this.params.title = document.title || '';
            this.params.referrer = this.referrer;
            this.params.token = this.getCookie('token') || '';
        }

        if (window && window.screen) {
            this.query = window.location.search || '';
            this.params.sh = window.screen.height || 0;
            this.params.sw = window.screen.width || 0;
            this.params.cd = window.screen.colorDepth || 0;
            try{
                this.queryparams = this.parseQuery(this.query);
            }
            catch(e) {
                this.queryparams = new Object();
            }
        }

        if (navigator) {
            this.params.lang = navigator.language || '';
        }
        this.params.requestid = this.requestid();
        this.params.pvid = this.pvid();
        this.params.uvid = this.uvid();
        this.isinit = true;
        this.pageLoadedTime();
    },

    init: function(params) {
        if (params && typeof params == 'object') {
            for (var i in params) {
                this.params[i] = params[i];
            }
        }

        if (!this.isinit) {
            this.isinit = true;
            this._init();
        }
        if (this.params.requestid == '') {
            this.params.requestid = this.requestid();
        }
        if (this.params.pvid == '') {
            this.params.pvid = this.pvid();
        }
        if (this.params.uvid == '') {
            this.params.uvid = this.uvid();
        }
        if (typeof(params.desc) == 'undefined' || params.desc == '') {
            this.params.desc = this.params.requestid;
        }
        this.send();
    },

    send: function() {
        var args = '';
        for(var i in this.params) {
            if(args != '') {
                args += '&';
            }
            if (typeof this.params[i] == 'object') {
                args += i + '=' + JSON.stringify(this.params[i]);
            } else {
                args += i + '=' + encodeURIComponent(this.params[i]);
            }
        }

        var img = new Image(1, 1);
        img.src = '//data.darryring.com/aws.gif?' + args;
    },

    pageLoadedTime: function() {
        var t = new Date().getTime();
        var _this = this;
        function aws_pageloaded() {
            if (document.readyState == 'complete') {
                var t1 = new Date().getTime();
                var ut = (t1 - t)/1000;
                ut = Math.round(ut);
                var desc = _this.params.requestid + ',' + ut;
                _dr.init({'account': 'DarryRing-WAP', 'event': 'pageloadedtime', 'desc': desc});
            } else {
                window.requestAnimationFrame(aws_pageloaded);
            }
        }
        window.requestAnimationFrame(aws_pageloaded);
    },

    action: function() {
        var that = this;
        var drgalist = document.querySelectorAll('[' + that.queryattr + ']');
        for (var i=0; i<drgalist.length; i++) {
            var data = JSON.parse(drgalist[i].getAttribute('dr-aws'));
            if (data && data.aws_bind) {
                (function(d){
                    drgalist[i].addEventListener(data.aws_bind, function(evt){
                        that.init(d);
                    });
                })(data);
            }
        }
    },

    setOrder: function(mjson) {
        if (typeof mjson == 'object') {
            if (typeof(sessionStorage) != 'undefined') {
                sessionStorage.setItem(this.orderkey, JSON.stringify(mjson));
            }
        }
    },

    getOrder: function() {
        if (typeof(sessionStorage) != 'undefined') {
            var ostr = sessionStorage.getItem(this.orderkey);
            if (!ostr) {
                return new Object();
            }
            var ojson = JSON.parse(ostr);
            if (!ojson) {
                return new Object();
            }
            return ojson;
        }
    },

    lSet: function(key, val) {
        this.setCookie(key, val, 1800);
    },

    lGet: function(key) {
        var val = this.getCookie(key) || '';
        return val;
    },

    time2DateFormat: function(ctime, sept) {
        var ndate = new Date();
        ndate.setTime(ctime);
        if (!sept) {
            sept = '/';
        }
        var y = ndate.getFullYear();
        var m = this.preZero(ndate.getMonth() + 1);
        var d = this.preZero(ndate.getDate());
        var h = this.preZero(ndate.getHours());
        var mm = this.preZero(ndate.getMinutes());
        var s = this.preZero(ndate.getSeconds());
        return y+sept+m+sept+d+' '+h+':'+mm+':'+s;
    },

    requestid: function() {
        return this.guid();
    },

    pvid: function() {

        var pvid = this.getCookie(this.pvidkey);

        var pvtime = this.getCookie(this.pvidkey+'_utime');

        var curtime = new Date().getTime();
        if (!pvtime) {
            pvtime = curtime;
        }

        var setd = new Date();
        setd.setTime(pvtime);
        var sd = setd.getDate();

        var cd = new Date().getDate();

        var isnewflage = this.isNewerSrcFlag();
        if (pvid && (sd == cd) && !isnewflage) {
            this.setCookie(this.pvidkey, pvid, 1800);
            this.setCookie(this.pvidkey+'_utime', curtime, 1800);
            return pvid;
        }
        pvid = this.guid();
        this.setCookie(this.pvidkey, pvid, 1800);
        this.setCookie(this.pvidkey+'_utime', curtime, 1800);

        return pvid;
    },

    uvid: function() {
        var uvid = this.getCookie(this.uvidkey);
        if (uvid) {
            this.setCookie(this.uvidkey, uvid, 31536000);
            return uvid;
        }
        uvid = this.guid();
        this.setCookie(this.uvidkey, uvid, 31536000);

        return uvid;
    },

    isNewerSrcFlag: function() {
        if (this.ischannel()) {
            this.sourceflag = this.channel;
        }
        else if (this.isad()) {
            this.sourceflag = this.source + this.medium;
        }
        else if (this.isseo()) {
            this.sourceflag = this.seo;
        }
        if (typeof(sessionStorage) != 'undefined') {
            if (this.sourceflag) {
                var oldflag = this.getCookie(this.sourceflagkey) || '';
                if (oldflag == '' || oldflag != this.sourceflag ) {
                    this.setCookie(this.sourceflagkey, this.sourceflag, 1800);
                    return true;
                }
            }
        }

        return false;
    },

    ischannel: function(){
        if (this.queryparams && this.queryparams['channel']) {
            this.channel = this.queryparams['channel'];
            return true;
        }
        else {
            return false;
        }
    },

    isad: function(){
        this.source = this.queryparams['utm_source'] || this.queryparams['?utm_source'] || this.queryparams[' utm_source'];
        this.medium = this.queryparams['utm_medium'];
        if (this.source && this.medium) {
            return true;
        }
        else {
            return false;
        }
    },

    isseo: function(){
        if (this.referrer == '') {
            return false;
        }
        var reg = /^http(s)?:\/\/([^\/\?])(.*)/g
        var up = this.parseUrl(this.referrer);
        var host = up.host;
        if (!host) {
            return false;
        }
        var re = /(darryring\.com)|(localhost)|(darry\.udesk)|(alipay\.com)|(chinapay\.com)|(qq\.com)|(weixinbridge\.com)/g;
        if (re.test(host)) {
            return false;
        }
        else {
            this.seo = host;
            return true;
        }
    },

    parseUrl(url) {
        var a = document.createElement('a');
        a.href = url;
        return {
            source: url,
            protocol: a.protocol.replace(':',''),
            host: a.hostname,
            port: a.port,
            query: a.search,
            params: (function(){
                var ret = {},
                    seg = a.search.replace(/^\?/,'').split('&'),
                    len = seg.length, i = 0, s;
                for (;i<len;i++) {
                    if (!seg[i]) { continue; }
                    s = seg[i].split('=');
                    ret[s[0]] = s[1];
                }
                return ret;
            })(),
            file: (a.pathname.match(/\/([^\/?#]+)$/i) || [,''])[1],
            hash: a.hash.replace('#',''),
            path: a.pathname.replace(/^([^\/])/,'/$1'),
            relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [,''])[1],
            segments: a.pathname.replace(/^\//,'').split('/')
        };
    },

    parseQuery(query) {
        if (query == '') {
            return new Object();
        }
        //鍘绘帀?
        query = query.replace('?', '');

        qarr = query.split('&');

        var params = new Object();
        var tmparr = [];
        var key = '';
        for (var i = 0; i < qarr.length; i++) {
            tmparr = qarr[i].split('=');
            key = tmparr[0];
            try{
                params[key] = decodeURIComponent(tmparr[1].replace('?', ''));
            } catch(e) {
                params[key] = '';
            }
        }

        return params;
    },

    s4: function() {
        return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    },

    guid: function() {
        return (this.s4()+this.s4()+"-"+this.s4()+"-"+this.s4()+"-"+this.s4()+"-"+this.s4()+this.s4()+this.s4());
    },

    preZero: function(val) {
        if (val < 10) {
            val = '0' + val;
        }
        return val;
    },

    setCookie: function(c_name, value, expires, domain, path){

        var exdate = new Date();
        exdate.setTime(exdate.getTime() + expires * 1000);
        var expires_str = ((expires == null) ? '' : ';expires=' + exdate.toGMTString());


        if (domain == null || domain == '') {
            var re = /^([^\.]+\.){1}/
            domain = document.domain.replace(re, '.');
            domain = ';domain='+domain;
        }


        if (path == null || path == '') {
            path = ';path=/';
        }


        document.cookie = c_name + '=' + escape(value) + domain + path + expires_str;
    },

    getCookie: function(c_name) {
        c_start=document.cookie.indexOf(c_name + '=')
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(';', c_start);
            if (c_end == -1) c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
        return '';
    }
};
_dr._init();