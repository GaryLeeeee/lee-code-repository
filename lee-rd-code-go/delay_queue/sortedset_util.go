package delay_queue

import (
	"fmt"
	"github.com/go-redis/redis"
)

type SortedsetUtil struct {
	rd  *redis.Client
	key string // redis key
}

type MemberWithScore struct {
	Member string
	Score  float64
}

func NewSortedsetUtil(r *redis.Client, key string) *SortedsetUtil {
	return &SortedsetUtil{
		rd:  r,
		key: key,
	}
}

func (this *SortedsetUtil) GetCount() (int64, error) {
	return this.rd.ZCard(this.key).Result()
}

func (this *SortedsetUtil) Peek(num int64) ([]MemberWithScore, error) {
	res, err := this.rd.ZRangeWithScores(this.key, 0, num).Result()
	if err != nil {
		return nil, err
	}
	memberWithScoreList := make([]MemberWithScore, 0)
	for _, v := range res {
		memberWithScoreList = append(memberWithScoreList, MemberWithScore{
			Member: fmt.Sprintf("%v", v.Member),
			Score:  v.Score,
		})
	}
	return memberWithScoreList, nil
}

func (this *SortedsetUtil) MultiDel(keys []interface{}) error {
	_, err := this.rd.ZRem(this.key, keys...).Result()
	return err
}

func (this *SortedsetUtil) Push(member MemberWithScore) error {
	_, err := this.rd.ZAdd(this.key, redis.Z{
		Score:  member.Score,
		Member: member.Member,
	}).Result()
	return err
}
